package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;


import org.firstinspires.ftc.robotcore.external.Telemetry;


public class DriveTrain {

    DcMotor LF;
    DcMotor LR;
    DcMotor RF;
    DcMotor RR;
    double leftTargetDistance;
    double rightTargetDistance;
    private ElapsedTime runtime = new ElapsedTime();

    static final double     COUNTS_PER_MOTOR_REV    = 0 ;    // eg: Bilda 5203 Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 0;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);

    public DriveTrain(HardwareMap hardwareMap, boolean useEncoders ) {

        LF = hardwareMap.dcMotor.get("LF");
        LF.setPower(0);
        LF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LF.setDirection(DcMotor.Direction.REVERSE);

        LR = hardwareMap.dcMotor.get("LR");
        LR.setPower(0);
        LR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LR.setDirection(DcMotor.Direction.REVERSE);

        RF = hardwareMap.dcMotor.get("RF");
        RF.setPower(0);
        RF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        RR = hardwareMap.dcMotor.get("RR");
        RR.setPower(0);
        RR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        if( useEncoders ) {
            LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            LR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            RR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            LF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            LR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            RF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            RR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        } else {
            LF.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            LR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            RF.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            RR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }

    public void drive(double x, double y, double strafe) {

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio, but only when
        // at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(strafe), 1);
        double frontLeftPower = (y + x + strafe) / denominator;
        double backLeftPower = (y - x + strafe) / denominator;
        double frontRightPower = (y - x - strafe) / denominator;
        double backRightPower = (y + x - strafe) / denominator;

        LF.setPower(frontLeftPower);
        LR.setPower(backLeftPower);
        RF.setPower(frontRightPower);
        RR.setPower(backRightPower);
    }

    public void drive( double speed, double leftInches, double rightInches, double timeout,
                       OpModeIsActive opMode, Telemetry telemetry ) {

        // Ensure that the opmode is still active
        if (opMode.isActive()) {

            // reset the timeout time
            runtime.reset();

            startMoving( speed, leftInches, rightInches );

            /* isBusy for left and right motor is used with && not || since it is safer*/
            while( opMode.isActive() &&
                    runtime.seconds() < timeout &&
                    !hasReachedTarget() ) {

                displayPosition( telemetry, "Running" );
            }

            stopMoving();

            displayPosition( telemetry, "Stopped" );

            //  sleep(250);   //pause after each move
        }
    }

    public void startMoving( double speed, double leftInches, double rightInches ) {

        leftTargetDistance = leftInches;
        rightTargetDistance = rightInches;

        // Determine new target position, and pass to motor controller
        int newLeftTarget = LF.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
        LF.setTargetPosition( newLeftTarget );

        int newRightTarget = RF.getCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH);
        RF.setTargetPosition( newRightTarget );

        // Turn On RUN_TO_POSITION
        LF.setMode( DcMotor.RunMode.RUN_TO_POSITION );
        RF.setMode( DcMotor.RunMode.RUN_TO_POSITION );

        LF.setPower( Math.abs(speed) );
        RF.setPower( Math.abs(speed) );
    }

    public void stopMoving() {
        // Stop all motion;
        LF.setPower( 0 );
        RF.setPower( 0 );

        // Turn off RUN_TO_POSITION
        LF.setMode( DcMotor.RunMode.RUN_USING_ENCODER );
        RF.setMode( DcMotor.RunMode.RUN_USING_ENCODER );
    }

    public boolean hasReachedTarget() {

        return !(LF.isBusy() && RF.isBusy());
    }

    public void displayPosition( Telemetry telemetry, String status ) {

        telemetry.addData("Target Distance", "%4.2f\" : %4.2f\"", leftTargetDistance, rightTargetDistance );
        telemetry.addData("Delta Distance", "%4.2f\" : %4.2f\"",
                ( LF.getTargetPosition() - LF.getCurrentPosition() ) / COUNTS_PER_INCH,
                ( RF.getTargetPosition() - RF.getCurrentPosition() ) / COUNTS_PER_INCH );
        telemetry.addData("Target", "Running to %7d : %7d", LF.getTargetPosition(), RF.getTargetPosition() );
        telemetry.addData("Position", "Running at %7d : %7d", LF.getCurrentPosition(), RF.getCurrentPosition() );
        telemetry.addData("Status", status );
        telemetry.update();
    }
}