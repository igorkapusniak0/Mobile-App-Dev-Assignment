package ie.setu.madassignemtv2.models

import org.junit.Assert.*
import org.junit.Test
import kotlin.test.assertFailsWith

class UserUnitTest {

    @Test
    fun `creating valid user succeeds`() {
        val user = User()
        user.name = "Alice"
        user.password = "securePassword"
        user.language = "eng"
        user.darkMode = true

        assertEquals("Alice", user.name)
        assertEquals("securePassword", user.password)
        assertEquals("eng", user.language)
        assertEquals(true, user.darkMode)
        assertTrue(user.collections.isEmpty())
        assertTrue(user.sets.isEmpty())
    }

    @Test
    fun `name too short throws exception`() {
        val user = User()
        val exception = assertFailsWith<IllegalArgumentException> {
            user.name = "A"
        }
    }

    @Test
    fun `name too long throws exception`() {
        val user = User()
        assertFailsWith<IllegalArgumentException> {
            user.name = "A".repeat(21)
        }
    }

    @Test
    fun `password too short throws exception`() {
        val user = User()
        assertFailsWith<IllegalArgumentException> {
            user.password = "1234"
        }
    }

    @Test
    fun `password too long throws exception`() {
        val user = User()
        assertFailsWith<IllegalArgumentException> {
            user.password = "a".repeat(31)
        }
    }

    @Test
    fun `invalid language throws exception`() {
        val user = User()
        assertFailsWith<IllegalArgumentException> {
            user.language = "de"
        }
    }

    @Test
    fun `valid languages can be set`() {
        val user = User()
        user.language = "pl"
        assertEquals("pl", user.language)
        user.language = "eng"
        assertEquals("eng", user.language)
    }

    @Test
    fun `collections and sets default to empty lists`() {
        val user = User()
        assertTrue(user.collections.isEmpty())
        assertTrue(user.sets.isEmpty())
    }

    @Test
    fun `toString contains all key values`() {
        val user = User()
        user.name = "username"
        user.password = "password"
        user.language = "eng"
        user.darkMode = true
        val collection = LegoCollection()
        val set = LegoSet()
        user.collections.add(collection)
        user.sets.add(set)

        val text = user.toString()

        assertTrue(text.contains("username"))
        assertTrue(text.contains("password"))
        assertTrue(text.contains("en"))
        assertTrue(text.contains("true"))
    }
}
