package wh.MusicWorks.MusicPlayer;

import java.io.File;
import java.util.List;

import wh.MusicWorks.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author ÍõºÆ
 * @version 1.1 2010-11-20
 */
public class FileListAdapter extends BaseAdapter
{
	private LayoutInflater mInflater;
	private Bitmap mRootIcon;
	private Bitmap mBackIcon;
	private Bitmap mFolderIcon;
	private Bitmap mDocumentIcon;
	private List<String> mItems;
	private List<String> mPaths;
	private ViewHolder mHolder;

	public FileListAdapter(Context context,List<String> it,List<String> pa)
	{
		mInflater = LayoutInflater.from(context);
		mItems = it;
		mPaths = pa;
		mRootIcon = BitmapFactory.decodeResource(context.getResources(),R.drawable.back01);
		mBackIcon = BitmapFactory.decodeResource(context.getResources(),R.drawable.back02);
		mFolderIcon = BitmapFactory.decodeResource(context.getResources(),R.drawable.folder);
		mDocumentIcon = BitmapFactory.decodeResource(context.getResources(),R.drawable.doc);
	}
  
	public int getCount()
	{
		return mItems.size();
	}

	public Object getItem(int position)
	{
		return mItems.get(position);
	}
  
	public long getItemId(int position)
	{
		return position;
	}
  
	public View getView(int position,View convertView,ViewGroup parent)
	{
		if(convertView == null)
			convertView = createViewToShow();
		else
			mHolder = (ViewHolder) convertView.getTag();

		File f=new File(mPaths.get(position).toString());

		setListItemByItemList(position, f);
		
		return convertView;
	}

	private void setListItemByItemList(int position, File f) {
		if(mItems.get(position).toString().equals("b1"))
			setListItemToRoot();
		else if(mItems.get(position).toString().equals("b2"))
			setListItemToBack();
		else
			setListItemNormalFile(f);
	}

	private void setListItemNormalFile(File f) {
		mHolder.text.setText(f.getName());
		if(f.isDirectory())
			mHolder.icon.setImageBitmap(mFolderIcon);
		else
			mHolder.icon.setImageBitmap(mDocumentIcon);
	}

	private void setListItemToBack() {
		mHolder.text.setText("Back to ..");
		mHolder.icon.setImageBitmap(mBackIcon);
	}

	private void setListItemToRoot() {
		mHolder.text.setText("Back to /");
		mHolder.icon.setImageBitmap(mRootIcon);
	}

	private View createViewToShow() {
		View convertView;
		convertView=mInflater.inflate(R.layout.file_row, null);
		mHolder=findHolderByID(convertView);
		convertView.setTag(mHolder);
		return convertView;
	}

	private ViewHolder findHolderByID(View convertView) {
		ViewHolder holder;
		holder = new ViewHolder();
		holder.text = (TextView) convertView.findViewById(R.id.text);
		holder.icon = (ImageView) convertView.findViewById(R.id.icon);
		return holder;
	}

	private class ViewHolder
	{
		TextView text;
		ImageView icon;
	}
}
