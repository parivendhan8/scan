package techy.ap.scan;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;

import java.nio.charset.StandardCharsets;

import me.dm7.barcodescanner.zbar.BarcodeFormat;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class scanActivity extends AppCompatActivity implements ZBarScannerView.ResultHandler {

    ZBarScannerView mScannerView;
    private FrameLayout layout;

    ImageScanner mScanner;

    private static final String TAG = "scanActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.scan_fragment);

        if (!isCameraAvailable()) {
            // Cancel request if there is no rear-facing camera.
            cancelRequest();
            return;
        }

        ViewGroup contentFrame = (ViewGroup) findViewById(R.id.container);
        mScannerView = new ZBarScannerView(this);
        contentFrame.addView(mScannerView);


    }

    public void scanData(byte[] data) {


        Image barcode = new Image("Y800");
        barcode.setData(data);

        int result = mScanner.scanImage(barcode);

        if (result != 0) {
            SymbolSet syms = mScanner.getResults();
            final Result rawResult = new Result();
            for (Symbol sym : syms) {
                // In order to retreive QR codes containing null bytes we need to
                // use getDataBytes() rather than getData() which uses C strings.
                // Weirdly ZBar transforms all data to UTF-8, even the data returned
                // by getDataBytes() so we have to decode it as UTF-8.
                String symData;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    symData = new String(sym.getDataBytes(), StandardCharsets.UTF_8);
                } else {
                    symData = sym.getData();
                }
                if (!TextUtils.isEmpty(symData)) {
                    rawResult.setContents(symData);
                    rawResult.setBarcodeFormat(BarcodeFormat.getFormatById(sym.getType()));
                    break;
                }
            }
        }
    }

    private boolean isCameraAvailable() {

        PackageManager pm = getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    private void cancelRequest() {

        Toast.makeText(this, "No Camera", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void handleResult(Result rawResult) {

        Log.d(TAG, "handleResult: " + rawResult.getContents());

        Toast.makeText(this, "" + rawResult.getContents() , Toast.LENGTH_SHORT).show();

        startActivity(new Intent(getApplicationContext(), scanActivity.class));

    }

    @Override
    protected void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }


    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }
}
