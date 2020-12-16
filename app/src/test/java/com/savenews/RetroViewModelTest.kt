package com.savenews

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.savenews.model.Characters
import com.savenews.model.Image
import com.savenews.model.PostInfo
import com.savenews.repository.RetrofitRepository
import com.savenews.viewmodel.RetroViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RetroViewModelTest {

    lateinit var retroViewModel: RetroViewModel

    @Mock
    lateinit var retrofitRepository: RetrofitRepository
    private lateinit var postInfoEmptyList: List<Characters>
    private val mockList: MutableList<Characters> = mutableListOf()
    private val mockLiveData: MutableLiveData<PostInfo> = MutableLiveData()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this);
        this.retroViewModel = RetroViewModel(this.retrofitRepository)

        mockData()
    }

    @Test
    fun fetchPostInfoFromRepositoryTest() {
        `when`(retrofitRepository.fetchPostInfoSingle()).thenReturn(mockLiveData)
        retroViewModel.postInfoLiveData = mockLiveData
        Assert.assertNotNull(retroViewModel.postInfoLiveData.value)
        Assert.assertTrue(retroViewModel.postInfoLiveData.value?.characters?.size == 5)
    }

    private fun mockData() {
        postInfoEmptyList = emptyList()
        var image = Image("https://test.com")

        mockList.add(Characters(1,"JAVAMAASAI",null,null,null,null,null,Image("https://")))
        mockList.add(Characters(2,"JAVAMAASAI",null,null,null,null,null,Image("https://")))
        mockList.add(Characters(3,"JAVAMAASAI",null,null,null,null,null,Image("https://")))
        mockList.add(Characters(4,"JAVAMAASAI",null,null,null,null,null,Image("https://")))
        mockList.add(Characters(5,"JAVAMAASAI",null,null,null,null,null,Image("https://")))

        postInfoEmptyList = mockList.toList()
        mockLiveData.postValue(PostInfo("success",mockList.toList()))
    }
}