
package org.firstinspires.ftc.teamcode;


import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="TestTeleOp", group="Linear OpMode")
public class TestTeleOp extends LinearOpMode {

//    null huzz;

    public void runOpMode() {

        Indexer Indexee = new Indexer(hardwareMap);

        waitForStart();

        while( opModeIsActive() ) {
            int greenBallPos;
            greenBallPos = 2;
            Indexee.Index(greenBallPos);


        }
    }
}


