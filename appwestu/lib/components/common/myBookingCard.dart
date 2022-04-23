import 'package:flutter/material.dart';
import 'package:flutter_hooks/flutter_hooks.dart';

import 'package:appwestu/widgets/widgets.dart';

class MyBookingCard extends HookWidget {
  const MyBookingCard(
      this.index,
      this.iddocente,
      this.disdici,
      this.data,
      this.ora,
      this.name,
      this.lastname,
      this.idcorso,
      this.titolo,
      this.idripetizione,
      this.status,
      {Key? key})
      : super(key: key);

  final String data;
  final Function disdici;
  final int idcorso;
  final int iddocente;
  final int idripetizione;
  final int index;
  final String lastname;
  final String name;
  final String ora;
  final int status;
  final String titolo;

  @override
  Widget build(BuildContext context) {
    const arrayStatus = ['Attivo', 'Effettuato', 'Disdetto'];
    final plitetdDate = data.split('-');

    TextStyle getTextStyle() {
      return TextStyle(
          fontSize: 17, color: status == 1 ? Colors.grey : Colors.white);
    }


    return Container(
        height: 155,
        margin: const EdgeInsets.symmetric(horizontal: 20, vertical: 10),
        decoration: BoxDecoration(
            borderRadius: const BorderRadius.all(Radius.circular(20.0)),
            color: status == 1
                ? Colors.white
                : status == 2
                    ? Colors.yellow[300]
                    : Colors.red[300],
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
                        Text(name, style: getTextStyle()),
                        const SizedBox(
                          width: 5,
                        ),
                        Text(lastname, style: getTextStyle()),
                      ],
                    ),
                    Text(
                      arrayStatus[status - 1],
                      style: const TextStyle(
                          fontSize: 15, fontWeight: FontWeight.bold),
                    ),
                    const SizedBox(
                      height: 1,
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
                        if(status==1)...[GestureDetector(
              onTap: () {
                if (status != 3) {
                  disdici(idripetizione, 3, index);
                }
              },
              child: Container(
                alignment: Alignment.center,
                width: 100.0,
                padding: const EdgeInsets.symmetric(vertical: 20),
                decoration: BoxDecoration(
                    borderRadius: BorderRadius.circular(25),
                    color: const Color.fromARGB(255, 228, 103, 86)),
                child: Text(
                  status == 3 ? 'Disdetto' : 'Disdici',
                  style: simpleTextStyle(),
                ),
              ),
            )]
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
