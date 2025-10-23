package ie.setu.madassignemtv2.models

import org.junit.Assert.*
import org.junit.Test
import kotlin.test.assertFailsWith

class LegoSetUnitTest {

    @Test
    fun `creating valid lego set succeeds`() {
        val set = LegoSet()
        set.name = "Police Station"
        set.setNumber = 60246
        set.pieceCount = 894
        set.age = 12
        set.price = 99.99f
        set.isPublic = true
        set.collectionName = "Lego City"

        assertEquals("Police Station", set.name)
        assertEquals(60246, set.setNumber)
        assertEquals(894, set.pieceCount)
        assertEquals(12, set.age)
        assertEquals(99.99f, set.price)
        assertTrue(set.isPublic)
        assertEquals("Lego City", set.collectionName)
    }


    @Test
    fun `name too short throws exception`() {
        val set = LegoSet()
        assertFailsWith<IllegalArgumentException> {
            set.name = "Lego"
        }
    }

    @Test
    fun `name too long throws exception`() {
        val set = LegoSet()
        assertFailsWith<IllegalArgumentException> {
            set.name = "A".repeat(21)
        }
    }


    @Test
    fun `set number too low throws exception`() {
        val set = LegoSet()
        assertFailsWith<IllegalArgumentException> {
            set.setNumber = 0
        }
    }

    @Test
    fun `set number too high throws exception`() {
        val set = LegoSet()
        assertFailsWith<IllegalArgumentException> {
            set.setNumber = 10000000
        }
    }


    @Test
    fun `piece count too low throws exception`() {
        val set = LegoSet()
        assertFailsWith<IllegalArgumentException> {
            set.pieceCount = 49
        }
    }

    @Test
    fun `piece count too high throws exception`() {
        val set = LegoSet()
        assertFailsWith<IllegalArgumentException> {
            set.pieceCount = 1000000
        }
    }


    @Test
    fun `age too low throws exception`() {
        val set = LegoSet()
        assertFailsWith<IllegalArgumentException> {
            set.age = 2
        }
    }

    @Test
    fun `age too high throws exception`() {
        val set = LegoSet()
        assertFailsWith<IllegalArgumentException> {
            set.age = 19
        }
    }


    @Test
    fun `price too low throws exception`() {
        val set = LegoSet()
        assertFailsWith<IllegalArgumentException> {
            set.price = 0.99f
        }
    }

    @Test
    fun `price too high throws exception`() {
        val set = LegoSet()
        assertFailsWith<IllegalArgumentException> {
            set.price = 100000f
        }
    }


    @Test
    fun `default values are correct`() {
        val set = LegoSet()
        assertEquals("", set.name)
        assertEquals(0, set.setNumber)
        assertEquals(0, set.pieceCount)
        assertEquals(0, set.age)
        assertEquals(0f, set.price)
        assertFalse(set.isPublic)
        assertEquals("", set.collectionName)
    }

    @Test
    fun `toString contains all key values`() {
        val set = LegoSet()
        set.name = "Fire Truck"
        set.setNumber = 60231
        set.pieceCount = 256
        set.age = 8
        set.price = 29.99f
        set.isPublic = false
        set.collectionName = "Lego City"

        val text = set.toString()

        assertTrue(text.contains("Fire Truck"))
        assertTrue(text.contains("60231"))
        assertTrue(text.contains("256"))
        assertTrue(text.contains("8"))
        assertTrue(text.contains("29.99"))
    }
}
