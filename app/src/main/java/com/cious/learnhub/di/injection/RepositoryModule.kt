package com.cious.learnhub.di.injection

import com.cious.learnhub.data.repository.customer.CustomerRepository
import com.cious.learnhub.data.repository.customer.CustomerRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindCustomerRepository(customerRepositoryImpl: CustomerRepositoryImpl) : CustomerRepository
}