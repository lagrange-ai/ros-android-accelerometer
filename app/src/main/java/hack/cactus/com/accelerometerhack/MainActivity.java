package hack.cactus.com.accelerometerhack;

import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;

import org.ros.address.InetAddressFactory;
import org.ros.android.RosActivity;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;

import java.net.URI;

import hack.cactus.com.accelerometerhack.acceleration.AccelerationTalker;

public class MainActivity extends RosActivity {

    public MainActivity() {
        super("Example", "Example");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void init(NodeMainExecutor nodeMainExecutor) {
        AccelerationTalker exampleNode = new AccelerationTalker(getApplicationContext());
        WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        NodeConfiguration nodeConfiguration = NodeConfiguration.newPublic(ip, URI.create("http://192.168.43.130:11311"));
        nodeMainExecutor.execute(exampleNode, nodeConfiguration);
    }
}
