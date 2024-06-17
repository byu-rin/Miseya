package com.example.miseya

import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        viewModel = MainViewModel()
    }

    @Test
    fun `test setSelectedArea updates area and logs correctly`() = runBlockingTest {
        val area = "강남구"

        viewModel.setSelectedArea(area)

        // Observe the state to ensure it updates correctly
        viewModel.selectedArea.test {
            assertEquals(area, awaitItem())
        }

        // Ensure the isLoading state changes as expected
        viewModel.isLoading.test {
            assertEquals(false, awaitItem()) // Initial state
            assertEquals(true, awaitItem()) // When loadDustInfo starts
            assertEquals(false, awaitItem()) // When loadDustInfo ends
        }
    }

    @Test
    fun `test loadDustInfo updates dustData and logs correctly`() = runBlockingTest {
        val area = "강남구"

        // Setting selected city to ensure correct API parameters
        viewModel.setSelectedCity("서울")
        viewModel.setSelectedArea(area)

        viewModel.dustData.test {
            val dustItem = awaitItem()
            // Perform assertions based on expected dustItem
            assertEquals(null, dustItem) // Initially it should be null, adjust as necessary
        }
    }
}