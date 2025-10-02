package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.DriveTrain;


@TeleOp(name = "DemoTeleOp", group="Example")
public class TelePoop extends LinearOpMode {



    public void runOpMode() {

        DriveTrain DriveTrain = new DriveTrain( hardwareMap, false );

        waitForStart();

        while( opModeIsActive() ) {

            double leftStickY = -gamepad1.left_stick_y; // Remember, this is reversed!
            double leftStickX = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rightStickX = gamepad1.right_stick_x;

            telemetry.addData("leftStick X", leftStickX );
            telemetry.addData("leftStick Y", leftStickY );
            telemetry.addData("rightStick X", rightStickX );
            telemetry.update();

            DriveTrain.drive( leftStickX, leftStickY, rightStickX );

        }
    }
}