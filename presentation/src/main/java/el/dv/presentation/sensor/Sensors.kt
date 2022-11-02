package el.dv.presentation.sensor

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import el.dv.presentation.sensor.model.AndroidSensor

class AccelerometerSensor(
    context: Context
) : AndroidSensor(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_ACCELEROMETER,
    sensorType = Sensor.TYPE_ACCELEROMETER
)

class GyroscopeSensor(
    context: Context
) : AndroidSensor(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_GYROSCOPE,
    sensorType = Sensor.TYPE_GYROSCOPE
)
