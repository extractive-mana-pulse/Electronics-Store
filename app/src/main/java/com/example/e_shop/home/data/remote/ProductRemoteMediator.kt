package com.example.e_shop.home.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.paging.RemoteMediator.MediatorResult
import androidx.room.withTransaction
import com.example.e_shop.home.data.local.ProductsDatabase
import com.example.e_shop.home.data.local.ProductsEntity
import retrofit2.HttpException
import java.io.IOException

//@OptIn(ExperimentalPagingApi::class)
//class ProductRemoteMediator(
//    private val productDb: ProductsDatabase,
//    private val shopApi: ShopApi
//): RemoteMediator<Int, ProductsEntity>() {
//
//    override suspend fun load(
//        loadType: LoadType,
//        state: PagingState<Int, ProductsEntity>
//    ): MediatorResult {
//        return try {
//            val loadKey = when(loadType) {
//                LoadType.REFRESH -> 1
//                LoadType.PREPEND -> return MediatorResult.Success(
//                    endOfPaginationReached = true
//                )
//                LoadType.APPEND -> {
//                    val lastItem = state.lastItemOrNull()
//                    if(lastItem == null) {
//                        1
//                    } else {
//                        (lastItem.id / state.config.pageSize) + 1
//                    }
//                }
//            }
//
////            val products = shopApi.getAllProducts(
////                page = loadKey,
////                pageCount = state.config.pageSize
////            )
//
//            productDb.withTransaction {
//                if(loadType == LoadType.REFRESH) {
//                    productDb.productsDao.clearAll()
//                }
////                Todo: Not done yet
////                val productEntities = products.products.map { it.toProductEntity() }
////                productDb.productsDao.upsertAll(productEntities)
//            }
//
//            MediatorResult.Success(endOfPaginationReached = products.products.isEmpty())
//        } catch(e: IOException) {
//            MediatorResult.Error(e)
//        } catch(e: HttpException) {
//            MediatorResult.Error(e)
//        }
//    }
//}