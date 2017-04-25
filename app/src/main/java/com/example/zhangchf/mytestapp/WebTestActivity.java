package com.example.zhangchf.mytestapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.zhangchf.mytestapp.web.Comment;
import com.example.zhangchf.mytestapp.web.Post;
import com.example.zhangchf.mytestapp.web.User;
import com.example.zhangchf.mytestapp.web.WebAPI;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function6;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zhangchf on 30/03/2017.
 */

public class WebTestActivity extends AppCompatActivity {

    public static final String TAG = WebTestActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kotlin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        test();
    }

    private void test() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Log.d(TAG, "retrofit request:" + chain.request().url());
                return chain.proceed(chain.request());
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WebAPI.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(clientBuilder.build())
                .build();

        final WebAPI webAPI = retrofit.create(WebAPI.class);

        final Observable observable1 = webAPI.getPosts()
                .subscribeOn(Schedulers.io())
                .doOnNext(new Consumer<Response<Post[]>>() {
                    @Override
                    public void accept(@NonNull Response<Post[]> response) throws Exception {
                        Log.d(TAG, "observable1, getPost onNext, " + response);
                        if (response.code() > 300) throw new HttpException(response);
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Log.d(TAG, "observable1, getPost onError", throwable);
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d(TAG, "observable1, getPost onComplete");
                    }
                })
                .flatMap(new Function<Response<Post[]>, ObservableSource<Response<Comment[]>>>() {
                    @Override
                    public ObservableSource<Response<Comment[]>> apply(@NonNull Response<Post[]> postsResponse) throws Exception {
                        Log.d(TAG, "observable1, flatMap, " + postsResponse);
                        return webAPI.getComments(1);
                    }
                })
                .subscribeOn(Schedulers.io())
                .doOnNext(new Consumer<Response<Comment[]>>() {
                    @Override
                    public void accept(@NonNull Response<Comment[]> response) throws Exception {
                        Log.d(TAG, "observable1, onNext, " + response);
                        if (response.code() > 300) throw new HttpException(response);

                    }
                }).doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Log.d(TAG, "observable1, onError", throwable);
                    }
                }).doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d(TAG, "observable1, onComplete");
                    }
                });

        final Observable observable2 = webAPI.getUsers()
                .subscribeOn(Schedulers.io())
                .doOnNext(new Consumer<Response<User[]>>() {
                    @Override
                    public void accept(@NonNull Response<User[]> response) throws Exception {
                        Log.d(TAG, "observable2, onNext, "+ response);
                        if (response.code() > 300) throw new HttpException(response);
                    }
                }).doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Log.d(TAG, "observable2, onError", throwable);
                    }
                }).doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d(TAG, "observable2, onComplete");
                    }
                });

        final Observable observable3 = webAPI.getUserPosts(3)
                .subscribeOn(Schedulers.io())
                .doOnNext(new Consumer<Response<Post[]>>() {
                    @Override
                    public void accept(@NonNull Response<Post[]> response) throws Exception {
                        Log.d(TAG, "observable3, onNext, "+ response);
                        if (response.code() > 300) throw new HttpException(response);
                    }
                }).doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Log.d(TAG, "observable3, onError", throwable);
                    }
                }).doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d(TAG, "observable3, onComplete");
                    }
                });

        final Observable observable4 = webAPI.getUserPosts(4)
                .subscribeOn(Schedulers.io())
                .doOnNext(new Consumer<Response<Post[]>>() {
                    @Override
                    public void accept(@NonNull Response<Post[]> response) throws Exception {
                        Log.d(TAG, "observable4, onNext, "+ response);
                        if (response.code() > 300) throw new HttpException(response);
                    }
                }).doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Log.d(TAG, "observable4, onError", throwable);
                    }
                }).doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d(TAG, "observable4, onComplete");
                    }
                });

        final Observable observable5 = webAPI.getUserPosts(5)
                .subscribeOn(Schedulers.io())
                .doOnNext(new Consumer<Response<Post[]>>() {
                    @Override
                    public void accept(@NonNull Response<Post[]> response) throws Exception {
                        Log.d(TAG, "observable5, onNext, "+ response);
                        if (response.code() > 300) throw new HttpException(response);
                    }
                }).doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Log.d(TAG, "observable5, onError", throwable);
                    }
                }).doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d(TAG, "observable5, onComplete");

                    }
                });

        final Observable observable6 = webAPI.getUserPosts(6)
                .subscribeOn(Schedulers.io())
                .doOnNext(new Consumer<Response<Post[]>>() {
                    @Override
                    public void accept(@NonNull Response<Post[]> response) throws Exception {
                        Log.d(TAG, "observable6, onNext, "+ response);
                        if (response.code() > 300) throw new HttpException(response);
                    }
                }).doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Log.d(TAG, "observable6, onError", throwable);
                    }
                }).doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d(TAG, "observable6, onComplete");

                    }
                });


        Observable.zip(observable2, observable1, observable3, observable4, observable5, observable6, new Function6() {
                        @Override
                        public Object apply(@NonNull Object o, @NonNull Object o2, @NonNull Object o3, @NonNull Object o4, @NonNull Object o5, @NonNull Object o6) throws Exception {
                            Log.d(TAG, "zip, function apply");
                            return o;
                        }
                    })
                .subscribeOn(Schedulers.io())
                .doOnNext(new Consumer() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        Log.d(TAG, "zip, onNext, o=" + o);
                    }
                }).doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Log.d(TAG, "zip onError", throwable);
                    }
                }).subscribe(new Consumer() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        Log.d(TAG, "zip subscribe, onNext," + o);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Log.d(TAG, "zip subscribe, onError,", throwable);
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d(TAG, "zip subscribe, onComplet");
                    }
                });


        }



}
