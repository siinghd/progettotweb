import 'dart:math';
import 'package:appwestu/pages/auth/UserLoggedInState.dart';
import 'package:appwestu/utils/GenericRequests.dart';
import 'package:appwestu/utils/functions.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:go_router/go_router.dart';

PreferredSizeWidget appBarMain(BuildContext context, String title) {
  return AppBar(
    title: Center(
      child: Text(title,
          style: const TextStyle(
            fontSize: 25.0,
            fontWeight: FontWeight.bold,
          )),
    ),
    actions: <Widget>[
      IconButton(
        icon: const Icon(
          Icons.door_back_door_outlined,
          color: Colors.white,
        ),
        onPressed: () async {
          if (context.read<UserLoggedInState>().getRole == -1) {
            context.go('/');
            return;
          }

          final res = await GenericRequests.genericGet('/signout');
          if (res['status'] == 'fail') {
            showerrorConfirmDialog(
                context, 'Ops, qualcosa non va', res['message']);
            return;
          }
          logoutUserClient(context);
        },
      )
    ],
  );
}

InputDecoration textfieldDecorationForm(String hintText) {
  return InputDecoration(
      hintText: hintText,
      hintStyle: const TextStyle(
        color: Colors.white54,
      ),
      focusedBorder: const UnderlineInputBorder(
          borderRadius: BorderRadius.all(Radius.circular(5.0)),
          borderSide: BorderSide(color: Color.fromARGB(255, 25, 0, 167))),
      enabledBorder: const UnderlineInputBorder(
          borderSide: BorderSide(
              width: 2.0, color: Color.fromARGB(255, 255, 255, 255))));
}

TextStyle simpleTextStyle() {
  return const TextStyle(color: Colors.white, fontSize: 20.0);
}

showerrorConfirmDialog(BuildContext context, String title, String mesage) {
  showDialog(
      context: context,
      builder: (_) => AlertDialog(
            title: Text(title),
            content: Text(mesage),
            actions: <Widget>[
              TextButton(
                child: const Text('ok!'),
                onPressed: () {
                  Navigator.of(context).pop();
                },
              )
            ],
          ));
}

GestureDetector myButton(context, Function callback, String title,
        {width: 100.0}) =>
    GestureDetector(
      onTap: () {
        callback();
      },
      child: Container(
        alignment: Alignment.center,
        width: width,
        padding: const EdgeInsets.symmetric(vertical: 20),
        decoration: BoxDecoration(
            borderRadius: BorderRadius.circular(25),
            gradient: const LinearGradient(colors: [
              Color.fromARGB(45, 55, 72, 1),
              Color.fromARGB(74, 85, 104, 1)
            ])),
        child: Text(
          title,
          style: simpleTextStyle(),
        ),
      ),
    );

Row pagination(
    int currPage, int limit, Function setPage, int dataLength) {
  List<Widget> arr = [];
  if (currPage > 0) {
    arr.add(GestureDetector(
      onTap: () {
        setPage(max(0, currPage - 1));
      },
      child: Container(
        alignment: Alignment.center,
        width: 100.0,
        padding: const EdgeInsets.symmetric(vertical: 20),
        decoration: BoxDecoration(
                                borderRadius: BorderRadius.circular(25),
                                color: Colors.blue),
        child: Text(
          '${currPage - 1}',
          style: simpleTextStyle(),
        ),
      ),
    ));
  }

  arr.add(GestureDetector(
    onTap: () {},
    child: Container(
      alignment: Alignment.center,
      width: 100.0,
      padding: const EdgeInsets.symmetric(vertical: 20),
      decoration: BoxDecoration(
                                borderRadius: BorderRadius.circular(25),
                                color: const Color.fromARGB(255, 0, 71, 129)),
      child: Text(
        '$currPage',
        style: simpleTextStyle(),
      ),
    ),
  ));
  if (limit == dataLength) {
    arr.add(GestureDetector(
      onTap: () {
        setPage(currPage + 1);
      },
      child: Container(
        alignment: Alignment.center,
        width: 100.0,
        padding: const EdgeInsets.symmetric(vertical: 20),
        decoration: BoxDecoration(
                                borderRadius: BorderRadius.circular(25),
                                color: Colors.blue),
        child: Text(
          '${currPage + 1}',
          style: simpleTextStyle(),
        ),
      ),
    ));
  }
  return Row(
    mainAxisAlignment: MainAxisAlignment.center,
    children: arr);
}
