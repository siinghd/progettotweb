import 'package:flutter/material.dart';
import 'package:flutter_hooks/flutter_hooks.dart';
import 'package:go_router/go_router.dart';
import 'package:intl/intl.dart';
import 'package:provider/provider.dart';

import 'package:appwestu/pages/auth/UserLoggedInState.dart';
import 'package:appwestu/widgets/widgets.dart';

class BookingCard extends HookWidget {
  const BookingCard(this.index,this.iddocente, this.bookMe, this.data, this.ora, this.name,
      this.lastname, this.idcorso, this.titolo,
      {Key? key})
      : super(key: key);
  final int index;
  final Function bookMe;
  final String data;
  final int idcorso;
  final int iddocente;
  final String lastname;
  final String name;
  final String ora;
  final String titolo;
  
  @override
  Widget build(BuildContext context) {
    final plitetdDate = data.split('-');
    return Container(
        height: 150,
        margin: const EdgeInsets.symmetric(horizontal: 20, vertical: 10),
        decoration: BoxDecoration(
            borderRadius: const BorderRadius.all(Radius.circular(20.0)),
            color: Colors.white,
            boxShadow: [
              BoxShadow(color: Colors.black.withAlpha(100), blurRadius: 10.0),
            ]),
        child: Padding(
          padding: const EdgeInsets.symmetric(horizontal: 20.0, vertical: 10),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: <Widget>[
              Expanded(
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: <Widget>[
                    Text(
                      titolo,
                      style: const TextStyle(
                          fontSize: 28, fontWeight: FontWeight.bold),
                    ),
                    Row(
                      children: <Widget>[
                        Text(
                          name,
                          style:
                              const TextStyle(fontSize: 17, color: Colors.grey),
                        ),
                        const SizedBox(
                          width: 5,
                        ),
                        Text(
                          lastname,
                          style:
                              const TextStyle(fontSize: 17, color: Colors.grey),
                        ),
                      ],
                    ),
                    const SizedBox(
                      height: 10,
                    ),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: <Widget>[
                        Text(
                          '${plitetdDate[2]}-${plitetdDate[1]}-${plitetdDate[0]}',
                          style: const TextStyle(
                              fontSize: 20,
                              color: Colors.black,
                              fontWeight: FontWeight.bold),
                        ),
                                            const SizedBox(
                      width: 10,
                    ),
                         Text(
                          ora,
                          style: const TextStyle(
                              fontSize: 20,
                              color: Colors.black,
                              fontWeight: FontWeight.bold),
                        ),
                        const Spacer(),
                        GestureDetector(
                          onTap: () {
                            if (context.read<UserLoggedInState>().getRole ==
                                -1) {
                              context.go('/');
                              return;
                            }
                            bookMe(index,iddocente, idcorso, ora, data);
                          },
                          child: Container(
                            alignment: Alignment.center,
                            width: 100.0,
                            padding: const EdgeInsets.symmetric(vertical: 20),
                            decoration: BoxDecoration(
                                borderRadius: BorderRadius.circular(25),
                                color: Colors.blue),
                            child: Text(
                              'Prenota',
                              style: simpleTextStyle(),
                            ),
                          ),
                        ),
                      ],
                    )
                  ],
                ),
              ),
            ],
          ),
        ));
  }
}
