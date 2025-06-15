package games.enchanted.eg_text_customiser.common.serialization;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;

import java.util.List;
import java.util.function.Function;

public class ModCodecs {
    public static <T> Codec<List<T>> singleOrListCodec(Codec<T> codec) {
        return Codec.either(codec.listOf(), codec).xmap(
            either -> either.map(list -> list, List::of),
            list -> list.size() == 1 ? Either.right(list.getFirst()) : Either.left(list)
        );
    }


    public static class IdToElmMapper<I, E> {
        private final BiMap<I, E> map = HashBiMap.create();

        public Codec<E> codec(Codec<I> codec) {
            BiMap<E, I> inverseMap = this.map.inverse();
            return elmToIdCodec(codec, this.map::get, inverseMap::get);
        }

        public IdToElmMapper<I, E> put(I id, E value) {
            if(value == null) {
                throw new IllegalArgumentException("Value for id cannot be null, (id: '" + id + "')");
            }
            this.map.put(id, value);
            return this;
        }
    }


    public static <E, I> Codec<E> elmToIdCodec(Codec<I> codec, Function<I, E> IdToElm, Function<E, I> ElmToId) {
        return codec.flatXmap(
            input -> {
                E elm = IdToElm.apply(input);
                return elm != null ? DataResult.success(elm) : DataResult.error(() -> "Unknown element id: " + input);
            },
            input -> {
                I id = ElmToId.apply(input);
                if (id == null) {
                    return DataResult.error(() -> "Element with unknown id: " + input);
                }
                return DataResult.success(id);
            }
        );
    }
}
