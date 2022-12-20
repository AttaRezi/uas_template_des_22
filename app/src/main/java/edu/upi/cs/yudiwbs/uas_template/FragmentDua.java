package edu.upi.cs.yudiwbs.uas_template;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.upi.cs.yudiwbs.uas_template.databinding.FragmentDuaBinding;
import edu.upi.cs.yudiwbs.uas_template.databinding.FragmentSatuBinding;

public class FragmentDua extends Fragment {

    private FragmentDuaBinding binding;

    ArrayList<Hasil> alHasil = new ArrayList<>();
    AdapterHasil adapter;
    RecyclerView.LayoutManager lm;


    public FragmentDua() {
        // Required empty public constructor
    }

    public static FragmentDua newInstance(String param1, String param2) {
        FragmentDua fragment = new FragmentDua();
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
        binding = FragmentDuaBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        adapter = new AdapterHasil(alHasil);
        binding.rvHasil.setAdapter(adapter);

        lm = new LinearLayoutManager(getActivity());
        binding.rvHasil.setLayoutManager(lm);

        //supaya ada garis antar row
        binding.rvHasil.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        binding.buttonFrag2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("debugyudi","onclick");
                ApiAdvice.get("/advice", null, new JsonHttpResponseHandler() {

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
                        alHasil.add(new Hasil(rate));
                        adapter.notifyDataSetChanged();
                        Log.d("debugyudi", "rate" +":" +rate);
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
}