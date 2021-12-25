package com.vmakd1916gmail.com.login_logout_register.DB


import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vmakd1916gmail.com.login_logout_register.models.network.PageKey
import com.vmakd1916gmail.com.login_logout_register.models.network.PostResponse


@Dao
interface PostDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(posts: List<PostResponse>)

    @Query("SELECT * FROM post_table")
    fun pagingSource(): PagingSource<Int, PostResponse>

    @Query("DELETE FROM post_table")
    suspend fun clearAll()

}

@Dao
interface RemotePostKeyDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(pageKey: PageKey)

    @Query("SELECT * FROM pageKey WHERE id LIKE :id")
    fun getNextPageKey(id: Int): PageKey?

    @Query("DELETE FROM pageKey")
    suspend fun clearAll()
}
