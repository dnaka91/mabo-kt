package rocks.dnaka91.mabo

import kotlin.test.Test
import kotlin.test.assertEquals

class TupleTest {
    @Test
    fun tupleToList() {
        assertEquals(listOf(1, 2), Tuple2(1, 2).toList())
        assertEquals(listOf(1, 2, 3), Tuple3(1, 2, 3).toList())
        assertEquals(listOf(1, 2, 3, 4), Tuple4(1, 2, 3, 4).toList())
        assertEquals(listOf(1, 2, 3, 4, 5), Tuple5(1, 2, 3, 4, 5).toList())
        assertEquals(listOf(1, 2, 3, 4, 5, 6), Tuple6(1, 2, 3, 4, 5, 6).toList())
        assertEquals(listOf(1, 2, 3, 4, 5, 6, 7), Tuple7(1, 2, 3, 4, 5, 6, 7).toList())
        assertEquals(listOf(1, 2, 3, 4, 5, 6, 7, 8), Tuple8(1, 2, 3, 4, 5, 6, 7, 8).toList())
        assertEquals(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9), Tuple9(1, 2, 3, 4, 5, 6, 7, 8, 9).toList())
        assertEquals(
            listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10),
            Tuple10(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).toList(),
        )
        assertEquals(
            listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11),
            Tuple11(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11).toList(),
        )
        assertEquals(
            listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12),
            Tuple12(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12).toList(),
        )
    }

    @Test
    fun tupleToString() {
        assertEquals("(1, 2)", Tuple2(1, 2).toString())
        assertEquals("(1, 2, 3)", Tuple3(1, 2, 3).toString())
        assertEquals("(1, 2, 3, 4)", Tuple4(1, 2, 3, 4).toString())
        assertEquals("(1, 2, 3, 4, 5)", Tuple5(1, 2, 3, 4, 5).toString())
        assertEquals("(1, 2, 3, 4, 5, 6)", Tuple6(1, 2, 3, 4, 5, 6).toString())
        assertEquals("(1, 2, 3, 4, 5, 6, 7)", Tuple7(1, 2, 3, 4, 5, 6, 7).toString())
        assertEquals("(1, 2, 3, 4, 5, 6, 7, 8)", Tuple8(1, 2, 3, 4, 5, 6, 7, 8).toString())
        assertEquals("(1, 2, 3, 4, 5, 6, 7, 8, 9)", Tuple9(1, 2, 3, 4, 5, 6, 7, 8, 9).toString())
        assertEquals(
            "(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)",
            Tuple10(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).toString(),
        )
        assertEquals(
            "(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11)",
            Tuple11(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11).toString(),
        )
        assertEquals(
            "(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)",
            Tuple12(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12).toString(),
        )
    }
}
