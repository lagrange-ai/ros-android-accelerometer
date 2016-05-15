package hack.cactus.com.accelerometerhack.acceleration;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import org.ros.concurrent.CancellableLoop;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.Node;
import org.ros.node.NodeMain;
import org.ros.node.topic.Publisher;

import std_msgs.String;

/**
 * Created by stdima on 15.05.16.
 */
public class AccelerationTalker extends AbstractNodeMain {
    private Publisher<std_msgs.String> publisher = null;
    private SensorManager mSensorManager;
    private Context context;

    public AccelerationTalker() {
    }

    public AccelerationTalker(Context context) {
        this.context = context;
        mSensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);

    }

    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("my_node");
    }

    @Override
    public void onStart(final ConnectedNode connectedNode) {
        publisher = connectedNode.newPublisher("/android", "std_msgs/String");
        mSensorManager.registerListener(listener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI);
    }

    private SensorEventListener listener = new SensorEventListener() {
        public void onSensorChanged(SensorEvent e) {
            if (e.sensor.getType()==Sensor.TYPE_ACCELEROMETER) {
                //Log.d("!!!!LOGS!!!!", "Sending data");
                std_msgs.String data = publisher.newMessage();
                data.setData(java.lang.String.format("(%f, %f, %f)", e.values[0], e.values[1], e.values[2]));
                publisher.publish(data);
            }
        }
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}
    };

    @Override
    public void onShutdown(Node node) {

    }

    @Override
    public void onShutdownComplete(Node node) {

    }

    @Override
    public void onError(Node node, Throwable throwable) {

    }
}
