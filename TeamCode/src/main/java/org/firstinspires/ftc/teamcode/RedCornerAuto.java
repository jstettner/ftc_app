package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;

/**
 * Created by student on 11/9/17.
 */
@Autonomous(name = "RedCornerAuto")
public class RedCornerAuto extends NewAutonomous {
    RelicRecoveryVuMark column = RelicRecoveryVuMark.UNKNOWN;
    public Alliance getAlliance() {
        return Alliance.RED;
    }
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");

        // Initialize the hardware variables.
        FR = hardwareMap.get(DcMotor.class, "FR");
        FL = hardwareMap.get(DcMotor.class, "FL");
        BR = hardwareMap.get(DcMotor.class, "BR");
        BL = hardwareMap.get(DcMotor.class, "BL");
        CBL = hardwareMap.get(ColorSensor.class, "CBL");
        CBOT = hardwareMap.get(ColorSensor.class, "CBOT");
        JS = hardwareMap.get(Servo.class, "JS");
        lift = hardwareMap.get(DcMotor.class, "lift");
        SR = hardwareMap.get(Servo.class, "SR");
        SL = hardwareMap.get(Servo.class, "SL");

        FR.setZeroPowerBehavior(ZERO_POWER_BEHAVIOR);
        FL.setZeroPowerBehavior(ZERO_POWER_BEHAVIOR);
        BR.setZeroPowerBehavior(ZERO_POWER_BEHAVIOR);
        BL.setZeroPowerBehavior(ZERO_POWER_BEHAVIOR);
        lift.setZeroPowerBehavior(ZERO_POWER_BEHAVIOR);
        initGyro();
        initVuforia();
        //  initVuforia();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                imu.initialize(parameters);
//                initialized = true;
//            }
//        }).start();
        startingAngle = imu.getAngularOrientation().firstAngle; //grabbers facing away from wall
        telemetry.addData("start", startingAngle);
        telemetry.update();
        strafe(false);
        JS.setPosition(JEWEL_SERVO_UP);
        waitForStart();

        closeGrabber();
        delay(1000);
        lift.setPower(.4);
        delay(800);
        lift.setPower(0);
        column = getPicto();
        telemetry.addData("column", column);
        pushJewel();
        delay(500);
        drive(0,.3,0,1400);
        delay(500);
        driveUntilBack(.25, 6.7, .6); //center
        delay(500);
//        driveUntilRight(.22, 8, .9);
        drive(0,.3,0,150);
        delay(500);

        // these should become until left
        if (column == RelicRecoveryVuMark.CENTER || column == RelicRecoveryVuMark.UNKNOWN) {
            driveUntilBack(.25, 12.4, .5); //center
        } else if (column == RelicRecoveryVuMark.LEFT) {
            driveUntilBack(.25, 15.5, .5); //left
        } else if (column == RelicRecoveryVuMark.RIGHT) {
            driveUntilBack(.25, 6.5, .5); //right
        }

        turn(.2, 45);
        delay(500);
        lift.setPower(-.4);
        delay(600);
        lift.setPower(0);
        delay(500);
        openGrabberFlat();
        delay(1000);
        drive(0, 0, .3, 1800);
        delay(500);
        drive(0, 0, -.3, 200);
    }

//    public void run(int state) {
//        if (state == 0) {
//            closeGrabber();
//            delay(1000);
//            lift.setPower(.4);
//            delay(800);
//            lift.setPower(0);
//        } else if (state == 1) {
//            column = getPicto();
//        } else if (state == 10) {
//            pushJewel();
//        } else if (state == 2) {
//            delay(500);
//            drive(0, .2, 0, 2500);
//        } else if (state == 3) {
//            delay(500);
//            turn(.2, 86);
//                delay(500);
//        } else if (state == 4) {
//                delay(500);
//                if (column == RelicRecoveryVuMark.CENTER || column == RelicRecoveryVuMark.UNKNOWN) {
//                    drive(0, -.3, 0, 2000);
//                    turn(.2, 13);
//                } else if (column == RelicRecoveryVuMark.LEFT) {
//                    drive(0, -.3, 0, 2500);
//                    turn(.2, 13);
//                } else if (column == RelicRecoveryVuMark.RIGHT) {
//                    drive(0, -.3, 0, 1500);
//                    turn(.2, 13);
//                }
//            delay(500);
//        } else if (state == 5) {
//            lift.setPower(-.4);
//            delay(600);
//            lift.setPower(0);
//            delay(500);
//            openGrabberFlat();
//            delay(1000);
//        } else if (state == 6){
//            drive(0, 0, .3, 1800);
//        } else {
//            delay(500);
//            delay(500);
//            drive(0, 0, -.3, 200);
//        }
//    }
}
