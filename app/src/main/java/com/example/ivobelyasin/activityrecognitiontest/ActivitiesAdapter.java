package com.example.ivobelyasin.activityrecognitiontest;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.location.DetectedActivity;

public class ActivitiesAdapter extends ArrayAdapter<DetectedActivity> {

    ActivitiesAdapter(Context context,
                      ArrayList<DetectedActivity> detectedActivities) {
        super(context, 0, detectedActivities);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {

        DetectedActivity detectedActivity = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.detected_activity, parent, false);
        }

        TextView activityName = view.findViewById(R.id.activity_type);
        TextView activityConfidenceLevel = view.findViewById(R.id.confidence_percentage);

        if (detectedActivity != null) {
            activityName.setText(ActivityDetectionService.getActivityString(getContext(),

                    detectedActivity.getType()));
            activityConfidenceLevel.setText(getContext().getString(R.string.percent,
                    detectedActivity.getConfidence()));

        }

        return view;
    }

    public void updateActivities(ArrayList<DetectedActivity> detectedActivities) {
        HashMap<Integer, Integer> detectedActivitiesMap = new HashMap<>();
        for (DetectedActivity activity : detectedActivities) {
            detectedActivitiesMap.put(activity.getType(), activity.getConfidence());
        }

        ArrayList<DetectedActivity> temporaryList = new ArrayList<>();
        for (int i = 0; i < ActivityDetectionService.POSSIBLE_ACTIVITIES.length; i++) {
            int confidence = detectedActivitiesMap.containsKey(ActivityDetectionService.POSSIBLE_ACTIVITIES[i]) ?
                    detectedActivitiesMap.get(ActivityDetectionService.POSSIBLE_ACTIVITIES[i]) : 0;

            temporaryList.add(new
                    DetectedActivity(ActivityDetectionService.POSSIBLE_ACTIVITIES[i],
                    confidence));
        }

        this.clear();

        for (DetectedActivity detectedActivity: temporaryList) {
            this.add(detectedActivity);
        }
    }
}