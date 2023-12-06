package com.beodeulsoft.opencvdemo;

public class Car {

    private int slot; // 주차 슬롯 번호
    private boolean carEmpty; // 자리 사용 여부


    public Car() { //생성자
    }

    public Car(int slot, boolean carEmpty) {
        this.slot = slot;
        this.carEmpty = carEmpty;
    }


    public int getNumber() {
        return slot;
    } //주차 슬롯 번호 반환

    public void setNumber(int number) {
        this.slot = number;
    } //주차 슬롯 번호 설정

    public boolean getCarEmpty() {
        return carEmpty;
    } //자리 사용 여부 반환

    public void setCarEmpty(boolean carEmpty) { //자리 사용 여부 설정
        this.carEmpty = carEmpty;
    }

    public int getSlot() {
        return slot;
    } //주차 슬롯 번호를 반환

    @Override
    public String toString() { //car 객체의 정보를 문자열 형태로 반환
        return "Car{" +
                "number=" + slot +
                ", carEmpty=" + carEmpty +
                '}';
    }
}
