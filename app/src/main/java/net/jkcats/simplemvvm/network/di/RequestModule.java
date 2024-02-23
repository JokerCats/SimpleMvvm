package net.jkcats.simplemvvm.network.di;

import net.jkcats.simplemvvm.network.proxy.ClientProxy;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class RequestModule {

    @Singleton
    @Provides
    public ClientProxy providerProxy() {
        return ClientProxy.obtain();
    }
}
