/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package martintrollip.cats.app.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import martintrollip.cats.app.data.Result
import martintrollip.cats.app.data.model.Cat
import martintrollip.cats.app.data.source.CatsDataSource
import timber.log.Timber
import java.lang.Exception

/**
 * Implementation of the data source that adds a latency simulating network.
 */
object CatsRemoteDataSource : CatsDataSource {

    override fun observeCats(): LiveData<Result<List<Cat>>> {
        TODO("not implemented")
    }

    override fun observeCat(catId: String): LiveData<Result<Cat>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getCat(catId: String): Result<Cat> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getCats(): Result<List<Cat>> {
        try {
            val response = CatsApiService.catsApi.getImages().await()
            Timber.d(response.body().toString())
            if (response.isSuccessful) {
                return Result.Success(response.body()!!)
            }
        } catch (ex: Exception) {
            return Result.Error(ex)
        }
        return Result.Success(ArrayList())
    }

    override suspend fun save(cat: Cat) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun deleteAllCats() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
