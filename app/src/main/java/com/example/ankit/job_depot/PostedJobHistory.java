package com.example.ankit.job_depot;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ankit.job_depot.candidate.model.DAO.EmployerHistory;
import com.example.ankit.job_depot.candidate.model.DAO.JobsQuery;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostedJobHistory extends Fragment {
    String employerName;
    private List<ParseObject> jobDetails;
    Context context;
    public PostedJobHistory() {}
    public PostedJobHistory(Context context, String employerName) {
        this.context = context;
        this.employerName = employerName;
    }
    ListView listViewJobHistory;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View jobsView = inflater.inflate(R.layout.fragment_posted_job_history, container, false);
        listViewJobHistory = (ListView) jobsView.findViewById(R.id.listViewJobHistory);

        if(jobDetails==null){
            jobDetails=new ArrayList<ParseObject>();
            EmployerHistory jobsQuery=new EmployerHistory();
            jobDetails = jobsQuery.getJobHistory(employerName);
        }

        listViewJobHistory.setAdapter(new TimelineList(context, jobDetails));

        return jobsView;
    }
}

class TimelineList extends BaseAdapter {
    private List<ParseObject> elements;
    Context mContext;
    public TimelineList(Context mContext, List<ParseObject> elements) {
        this.mContext = mContext;
        this.elements = elements;
    }


    @Override
    public int getCount() {
        return elements.size();
    }

    @Override
    public ParseObject getItem(int position) {
        return elements.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
        View row;
        row = inflater.inflate(R.layout.job_history_listitem, parent, false);
        TextView title, detail;
        title = (TextView) row.findViewById(R.id.title);
        detail = (TextView) row.findViewById(R.id.detail);
        title.setText(getItem(position).get("JobName").toString());
        title.setMovementMethod(LinkMovementMethod.getInstance());
        detail.setText(getItem(position).get("updatedAt").toString());
        return (row);
    }
}