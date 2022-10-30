package com.github.andreyzim.numbers.numbers.data.cache

import com.github.andreyzim.numbers.numbers.data.NumberData
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class NumbersCacheDataSourceTest {

    @Test
    fun test_all_numbers_empty() = runBlocking {
        val dao = TestDao()
        val dataSource = NumbersCacheDataSource.Base(dao, TestMapper(5))

        val actual = dataSource.allNumbers()
        val expected = emptyList<NumberCache>()
        assertEquals(expected, actual)
    }

    @Test
    fun test_all_numbers_not_empty() = runBlocking {
        val dao = TestDao()
        val dataSource = NumbersCacheDataSource.Base(dao, TestMapper(5))

        dao.data.add(NumberCache("1", "fact1", 1))
        dao.data.add(NumberCache("2", "fact2", 2))

        val actual = dataSource.allNumbers()
        val expected = listOf(
            NumberData("1", "fact1"),
            NumberData("2", "fact2")
        )
        actual.forEachIndexed { index, numberData ->
            assertEquals(expected[index], numberData)
        }
    }

    @Test
    fun test_contains() = runBlocking {
        val dao = TestDao()
        val dataSource = NumbersCacheDataSource.Base(dao, TestMapper(5))

        dao.data.add(NumberCache("6", "fact6", 6))

        val actual = dataSource.contains("6")
        val expected = true
        assertEquals(expected, actual)
    }

    @Test
    fun test_save() = runBlocking {
        val dao = TestDao()
        val dataSource = NumbersCacheDataSource.Base(dao, TestMapper(8))

        dataSource.saveNumber(NumberData("8", "fact8"))
        assertEquals(NumberCache("8", "fact8", 8), dao.data[0])
    }

    @Test
    fun test_number_contains() = runBlocking {
        val dao = TestDao()
        val dataSource = NumbersCacheDataSource.Base(dao, TestMapper(9))

        dao.data.add(NumberCache("10", "fact10", 10))

        val actual = dataSource.number("10")
        val expected = NumberData("10", "fact10")
        assertEquals(expected, actual)
    }

    @Test
    fun test_number_does_not_exist() = runBlocking {
        val dao = TestDao()
        val dataSource = NumbersCacheDataSource.Base(dao, TestMapper(9))

        dao.data.add(NumberCache("10", "fact10", 10))

        val actual = dataSource.number("32")
        val expected = NumberData("", "")
        assertEquals(expected, actual)
    }

}

private class TestDao: NumbersDao {

    val data = mutableListOf<NumberCache>()

    override fun allNumbers(): List<NumberCache> = data

    override fun insert(number: NumberCache) {
        data.add(number)
    }

    override fun number(number: String): NumberCache? = data.find {it.number == number}
}

private class TestMapper(private val date: Long) : NumberData.Mapper<NumberCache> {
    override fun map(id: String, fact: String): NumberCache = NumberCache(id, fact, date)
}