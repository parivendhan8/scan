package techy.ap.scan;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class ScanFragment extends Fragment implements ZBarScannerView.ResultHandler {

    private static final String TAG = "ScanFragment";

    ZBarScannerView scannerView;
    private FrameLayout layout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.scan_fragment, null);

        layout = (FrameLayout) view.findViewById(R.id.container);
        scannerView = new ZBarScannerView(getContext());
        layout.addView(scannerView);
        return view;
    }

    @Override
    public void handleResult(Result rawResult) {

        Log.d(TAG, "handleResult: " + rawResult.getContents());

        Toast.makeText(getContext(), "" + rawResult.getContents() , Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onResume() {
        super.onResume();

        scannerView.setResultHandler(this);
        scannerView.startCamera();

    }

    @Override
    public void onPause() {
        super.onPause();

        scannerView.stopCamera();
    }


}
