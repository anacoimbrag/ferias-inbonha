package br.inhonhas.feriapp.service;

import java.io.IOException;
import java.util.HashMap;

import br.inhonhas.feriapp.PreferencesManager;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ana Coimbra on 05/01/2017.
 */

public class Service {

    private static PreferencesManager preferencesManager = PreferencesManager.getInstance();

    public static IService getInstance(boolean isAuth) {
        final HashMap<String, String> authHeader = getHeaders(isAuth);

        /**
         * Log interceptor (just for debug)
         */
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        /**
                         * Header interceptor (for auth headers and content-type)
                         */
                        Request original = chain.request();

                        Request.Builder requestBuilder = original.newBuilder();

                        if(!original.url().pathSegments().contains("registrations")) {
                            for (HashMap.Entry<String, String> header : authHeader.entrySet()) {
                                if (header.getValue() != null) {
                                    requestBuilder.addHeader(header.getKey(), header.getValue());
                                }
                            }
                        }

                        Request request = requestBuilder.build();

                        Response response = chain.proceed(request);
                        setAuthHeaders(response.headers());

                        return response;
                    }
                });


        OkHttpClient client = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ec2-54-212-207-175.us-west-2.compute.amazonaws.com/api/v1/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(IService.class);
    }

    private static HashMap<String, String> getAuthHeaders() {
        HashMap<String, String> header = new HashMap<>();
        header.put(
                "access-token",
                preferencesManager.getString("access-token")
        );
        header.put(
                "client",
                preferencesManager.getString("client")
        );
        header.put(
                "uid",
                preferencesManager.getString("uid")
        );

        return header;
    }

    private static HashMap<String, String> getHeaders(boolean isAuth) {
        HashMap<String, String> header;
        if (isAuth) header = getAuthHeaders();
        else header = new HashMap<>();

        header.put("Content-Type", "application/json");

        return header;
    }

    private static void setAuthHeaders(Headers headers) {
        if (headers.get("access-token") != null)
            preferencesManager
                    .setValue("access-token",
                            headers.get("access-token"));

        if (headers.get("uid") != null)
            preferencesManager.setValue("uid", headers.get("uid"));

        if (headers.get("client") != null)
            preferencesManager
                    .setValue("client", headers.get("client"));
    }

}
