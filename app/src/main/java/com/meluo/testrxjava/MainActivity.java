package com.meluo.testrxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // observer
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onNext(String value) {
                Log.e("TAG", "value = " + value);
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

        };

        //  observable
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("1");
                subscriber.onNext("2");
                subscriber.onNext("3");
                subscriber.onNext("4");
                subscriber.onCompleted();
            }
        });
//        observable.subscribe(observer);

        // just
        Observable<String> just = Observable.just("11", "22", "33", "44");
//        just.subscribe(observer);

        // from
        String[] strs = {"111", "222", "333", "444"};
//        Subscription subscription = Observable.from(strs)
//                .subscribe(observer);

        // map 对数据进行变换处理 一对一的转换
        Observable.from(new String[]{"1", "2"})
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return "---->" + s;
                    }
                })
                .subscribe(observer);

        // flatmap  一对多的转换
        ArrayList<Course> list1 = new ArrayList<>();
        list1.add(new Course("张三课程1"));
        list1.add(new Course("张三课程2"));
        list1.add(new Course("张三课程3"));
        list1.add(new Course("张三课程4"));
        Student zhangsan = new Student("张三", list1);

        ArrayList<Course> list2 = new ArrayList<>();
        list1.add(new Course("李四课程1"));
        list1.add(new Course("李四课程2"));
        list1.add(new Course("李四课程3"));
        list1.add(new Course("李四课程4"));
        Student lisi = new Student("李四", list2);

        Student[] students = {zhangsan, lisi};
        Observable.from(students)
                .subscribeOn(Schedulers.io())
                .filter(new Func1<Student, Boolean>() {
                    @Override
                    public Boolean call(Student student) {
                        return "张三".equals(student.getName());
                    }
                })
                .flatMap(new Func1<Student, Observable<Course>>() {
                    @Override
                    public Observable<Course> call(Student student) {
                        printThread();
                        return Observable.from(student.getCourses());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Course>() {
                    @Override
                    public void call(Course course) {
                        printThread();
                        Log.e("TAG", "course.name = " + course.getName());
                    }
                });
    }

    void printThread(){
        Log.e("TAG", "current Thread : " + Thread.currentThread().getName());
    }
}
