package edu.upi.cs.yudiwbs.uas_template;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpStatus;
import edu.upi.cs.yudiwbs.uas_template.databinding.FragmentSatuBinding;


public class FragmentSatu extends Fragment {

    private FragmentSatuBinding binding;

    public FragmentSatu() {
        // Required empty public constructor
    }

    public static FragmentSatu newInstance(String param1, String param2) {
        FragmentSatu fragment = new FragmentSatu();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSatuBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {binding.tvKeterangan.setText("Tunggu sebentar");
                //https://api.coindesk.com/v1/bpi/currentprice.json
                Log.d("debugyudi","onclick");
                ApiAdvice.get("advice", null, new JsonHttpResponseHandler() {

                    //hati2 success jsonobjek atau jsonarray
                    @Override
                    public void onSuccess(int statusCode,
                                          cz.msebera.android.httpclient.Header[] headers,
                                          org.json.JSONObject response) {
                        Log.d("debugyudi","onSuccess jsonobjek");

                        /*
                        Hasil JSON
                        {
                        "slip":
                            {
                                "id": 177,
                                "advice": "Everyone has their down days. Don't take it out on innocent bystanders."
                            }
                        } 
                        */
                        String rate= "";
                        try {
                            JSONObject slip = response.getJSONObject("slip");
                            rate = (String) slip.get("advice");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("debugyudi", "msg error" +":" +e.getMessage());
                        }
                        Log.d("debugyudi", rate +":");
                        binding.tvKeterangan.setText(rate);
                    }


                    @Override
                    public  void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String err, Throwable throwable)  {
                        Log.e("debugyudi", "error " + ":" + statusCode +":"+ err);
                    }
                });

            }
        });

        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}



