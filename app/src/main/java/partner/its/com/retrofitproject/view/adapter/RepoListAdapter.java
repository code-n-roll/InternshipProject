package partner.its.com.retrofitproject.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import partner.its.com.retrofitproject.R;
import partner.its.com.retrofitproject.model.RepoModel;

/**
 * Created by roman on 9.6.17.
 */

public class RepoListAdapter extends RecyclerView.Adapter<RepoListAdapter.ViewHolder>{
    private final ArrayList<RepoModel> mRepoModels;

    public RepoListAdapter(ArrayList<RepoModel> items){
        mRepoModels = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.content_cardview_repo, parent, false
        );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mRepoModels.get(position));
    }

    @Override
    public int getItemCount() {
        return mRepoModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView mNameRepo;
        private TextView mDescriptionRepo;

        public ViewHolder(View itemView) {
            super(itemView);

            mNameRepo = (TextView) itemView.findViewById(R.id.name_repo);
            mDescriptionRepo = (TextView) itemView.findViewById(R.id.description_repo);
        }

        public void bind(final RepoModel repoModel){
            mNameRepo.setText(repoModel.getName());
            mDescriptionRepo.setText(repoModel.getDescription());
        }
    }
}
