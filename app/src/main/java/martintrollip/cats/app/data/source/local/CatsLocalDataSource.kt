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
package martintrollip.cats.app.data.source.local

import androidx.lifecycle.LiveData
import martintrollip.cats.app.data.source.CatsDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import martintrollip.cats.app.data.model.Cat
import martintrollip.cats.app.data.Result

/**
 * Concrete implementation of a data source as a db.
 */
class CatsLocalDataSource internal constructor(
        private val catsDao: CatsDao,
        private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CatsDataSource {

    override fun observeCats(): LiveData<Result<List<Cat>>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getCats(): Result<List<Cat>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun refreshCats() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun deleteAllCats() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
