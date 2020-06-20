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
package martintrollip.cats.app.data.source

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import martintrollip.cats.app.data.Result
import martintrollip.cats.app.data.model.Cat

/**
 * Concrete implementation to load cats from the data sources into a cache.
 */
class DefaultCatsRepository(
        private val catsRemoteDataSource: CatsDataSource,
        private val catsLocalDataSource: CatsDataSource,
        private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) : CatsRepository {

    override suspend fun getCats(forceUpdate: Boolean): Result<List<Cat>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun refreshCats() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun observeCats(): LiveData<Result<List<Cat>>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun refreshCat(catId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun observeCat(catId: String): LiveData<Result<Cat>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getCat(catId: String, forceUpdate: Boolean): Result<Cat> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun saveCat(cat: Cat) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}