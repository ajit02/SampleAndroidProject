package com.example.sampleapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sampleapp.R;
import com.example.sampleapp.adapter.CustomListAdapter;
import com.example.sampleapp.interfaces.ViewData;
import com.example.sampleapp.model.MyData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CustomListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.

 */
public class CustomListFragment extends Fragment {

   private ListView listView;
   private TextView load_tv;
   private OnFragmentInteractionListener mListener;
   private CustomListAdapter customListAdapter;
   private SwipeRefreshLayout swipe_layout;
   MyData myData;

    public CustomListFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_list, container, false);
        load_tv=view.findViewById(R.id.loading_tv);
        swipe_layout=view.findViewById(R.id.swipe_refresh_layout);
        swipe_layout.setColorSchemeResources(R.color.refresh,R.color.refresh1,R.color.refresh2);
        swipe_layout.setOnRefreshListener(swipeListner);
        listView=view.findViewById(R.id.data_lv);
        //added to hide the default app name in the title.
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        myData=new MyData();
        //server call using retrofit
        getData();
        return view;

    }

    SwipeRefreshLayout.OnRefreshListener swipeListner= new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            getData();
            swipe_layout.setRefreshing(false);
        }
    };

    private void getData(){

        Retrofit retrofit= new Retrofit.Builder().baseUrl(ViewData.baseDataUrl).addConverterFactory(GsonConverterFactory.create()).build();
        ViewData apiService =
                retrofit.create(ViewData.class);
        final Call<MyData>  myDataCall= apiService.getData();

        myDataCall.enqueue(new Callback<MyData>() {
            @Override
            public void onResponse(Call<MyData> call, Response<MyData> response) {
                if(response.isSuccessful()){
                    myData= response.body();
                    String strMyData="";
                    if(myData!=null){
                        strMyData=  myData.getTitle();
                    }
                    if(strMyData!=null){
                        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
                        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(strMyData);
                    }
                    //set data in the listview
                    CustomListAdapter customListAdapter=new CustomListAdapter(getActivity(),myData);
                    listView.setAdapter(customListAdapter);
                    //Added to hide the loading view
                    if(customListAdapter.getCount()>0)
                        load_tv.setVisibility(View.GONE);

                }

                Toast.makeText(getActivity(), "Success ", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<MyData> call, Throwable t) {
                Toast.makeText(getActivity(), "Connection Failure Error(Server Error)", Toast.LENGTH_SHORT).show();
            }
        });
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
