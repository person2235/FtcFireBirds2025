package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.SwitchableLight;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/*
 * This OpMode shows how to use a color sensor in a generic
 * way, regardless of which particular make or model of color sensor is used. The OpMode
 * assumes that the color sensor is configured with a name of "sensor_color".
 *
 * There will be some variation in the values measured depending on the specific sensor you are using.
 *
 * If the color sensor supports adjusting the gain, you can increase the gain (a multiplier to make
 * the sensor report higher values) by holding down the A button on the gamepad, and decrease the
 * gain by holding down the B button on the gamepad. The AndyMark Proximity & Color Sensor does not
 * support this.
 *
 * If the color sensor has a light which is controllable from software, you can use the X button on
 * the gamepad to toggle the light on and off. The REV sensors don't support this, but instead have
 * a physical switch on them to turn the light on and off, beginning with REV Color Sensor V2. The
 * AndyMark Proximity & Color Sensor does not support this.
 *
 * If the color sensor also supports short-range distance measurements (usually via an infrared
 * proximity sensor), the reported distance will be written to telemetry. As of September 2025,
 * the only color sensors that support this are the ones from REV Robotics and the AndyMark
 * Proximity & Color Sensor. These infrared proximity sensor measurements are only useful at very
 * small distances, and are sensitive to ambient light and surface reflectivity. You should use a
 * different sensor if you need precise distance measurements.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list
 */
@TeleOp(name = "Indexer", group = "LinearOpMode")


public class Indexer {

    NormalizedColorSensor CS; //color sensor to index balls
    Servo SV; // servo to pop balls out
    DcMotor SM; //spinning motor of indexer

    private ElapsedTime runtime = new ElapsedTime();

    View relativeLayout;

    public void run() {
        relativeLayout.setBackgroundColor(Color.WHITE);
    }



    public void Index(int greenBallPos) { //greenBallPos is position of green ball in the pattern, can only be from 1-3, so 1 is gpp, 2 is pgp, 3 is ppg
        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);

        double SVdefault = 0;
        double SMPPR = 3895.9;
        double speed = 0.5;


        SM = hardwareMap.dcMotor.get("SM");
        SM.setPower(0);
        SM.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        SM.setDirection(DcMotor.Direction.REVERSE);
        SM.setMode(DcMotor.RunMode.RUN_USING_ENCODER); //3895.9 PPR for 43 rpm motor(goBilda YellowJacket)

        SV = hardwareMap.servo.get("SV");
        SV.setPosition(SVdefault);

        CS = hardwareMap.get(NormalizedColorSensor.class, "CS");

        NormalizedRGBA colors = CS.getNormalizedColors();

        






    }


}