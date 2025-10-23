package ie.setu.madassignemtv2.models

import org.junit.Assert.*
import org.junit.Test
import kotlin.test.assertFailsWith

class LegoCollectionUnitTest {
    @Test
    fun `creating valid collection succeeds`() {
        val collection = LegoCollection()
        collection.name = "Lego City"
        collection.description = "A collection of LEGO City sets"
        collection.creationDate = "2025-10-23"

        assertEquals("Lego City", collection.name)
        assertEquals("A collection of LEGO City sets", collection.description)
        assertEquals("2025-10-23", collection.creationDate)
        assertTrue(collection.sets.isEmpty())
        assertFalse(collection.isPublic)
    }

    @Test
    fun `name too short throws exception`() {
        val collection = LegoCollection()
        assertFailsWith<IllegalArgumentException> {
            collection.name = "A"
        }
    }

    @Test
    fun `name too long throws exception`() {
        val collection = LegoCollection()
        assertFailsWith<IllegalArgumentException> {
            collection.name = "A".repeat(21)
        }
    }

    @Test
    fun `description too short throws exception`() {
        val collection = LegoCollection()
        assertFailsWith<IllegalArgumentException> {
            collection.description = "abcd"
        }
    }

    @Test
    fun `description too long throws exception`() {
        val collection = LegoCollection()
        assertFailsWith<IllegalArgumentException> {
            collection.description = "a".repeat(31)
        }
    }

    @Test
    fun `numberOfSets returns correct count`() {
        val collection = LegoCollection()
        val set1 = LegoSet()
        val set2 = LegoSet()
        collection.sets.addAll(listOf(set1, set2))

        assertEquals(2, collection.numberOfSets())
    }

    @Test
    fun `default values are correct`() {
        val collection = LegoCollection()
        assertEquals("", collection.name)
        assertEquals("", collection.description)
        assertEquals("", collection.creationDate)
        assertTrue(collection.sets.isEmpty())
        assertFalse(collection.isPublic)
    }

    @Test
    fun `toString returns expected format`() {
        val collection = LegoCollection()
        collection.name = "Star Wars"
        collection.description = "My Star Wars sets"
        collection.creationDate = "2025-10-23"
        val output = collection.toString()

        assertTrue(output.contains("Star Wars"))
        assertTrue(output.contains("My Star Wars sets"))
        assertTrue(output.contains("2025-10-23"))
    }
}
