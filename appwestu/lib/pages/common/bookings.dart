import 'package:appwestu/components/common/bookingCard.dart';
import 'package:appwestu/utils/GenericRequests.dart';
import 'package:appwestu/utils/functions.dart';
import 'package:appwestu/widgets/widgets.dart';
import 'package:flutter/material.dart';
import 'package:flutter_hooks/flutter_hooks.dart';
import 'package:scrollable_positioned_list/scrollable_positioned_list.dart';
import 'package:top_snackbar_flutter/custom_snack_bar.dart';
import 'package:top_snackbar_flutter/top_snack_bar.dart';

class CommonBookings extends HookWidget {
  const CommonBookings({Key? key}) : super(key: key);

  final limit = 10;

  @override
  Widget build(BuildContext context) {
    final currentPage = useState(1);
    final isLoading = useState(false);
    final topContainer = useState(0.0);
    final topContainerIndex = useState(0);
    final selectedSubject = useState('Seleziona Materia');
    final itemsData = useState([]);
    final itemsDataFiltered = useState([]);
    final itemsDataSubject = useState([]);

    ItemScrollController controller = ItemScrollController();
    ItemPositionsListener itemPositionsListener =
        ItemPositionsListener.create();
    final forceRerender = useState(false);
    final Size size = MediaQuery.of(context).size;
    void controllerCallBack() {
      if(itemPositionsListener.itemPositions.value.isNotEmpty) {
 topContainer.value =
          itemPositionsListener.itemPositions.value.first.itemTrailingEdge;
      topContainerIndex.value =
          itemPositionsListener.itemPositions.value.first.index;
      }
     
    }

    void bookMeNow(index, iddocente, idcorso, data, ora) async {
      var res = await GenericRequests.genericPost(
          '/auth/common/prenotazionidisponibili', {
        'idcorso': idcorso,
        'iddocente': iddocente,
        'ora': ora,
        'data': data,
        'status': 1
      });
      if (res['status'] == 'fail') {
        if (res['error'] != null && res['error'] == 'LOGINREQUIRED') {
          logoutUserClient(context);
        } else {
          showTopSnackBar(
            context,
            CustomSnackBar.error(
              message: res['message'],
            ),
          );
        }
        if (controller.isAttached) {
          controller.scrollTo(
              index: index,
              duration: const Duration(seconds: 2),
              curve: Curves.easeInOutCubic);
        }
        return;
      }
      showTopSnackBar(
        context,
        CustomSnackBar.success(
          message: res['message'],
        ),
      );
      forceRerender.value = !forceRerender.value;
    }

    final getDataAndRender = useMemoized(
      () => () async {
        List<String> listItemsSubject = [];
        isLoading.value = true;
        var res = await GenericRequests.genericGet(
            '/getprenotazionidisponibili?currentpage=${currentPage.value}&limit=$limit');

        var resSubject =
            await GenericRequests.genericGet('/auth/common/getsubjects');

        isLoading.value = false;

        if (res['status'] == 'fail' || resSubject['status'] == 'fail') {
          if (res['error'] != null && res['error'] == 'LOGINREQUIRED') {
            logoutUserClient(context);
          } else {
            showTopSnackBar(
              context,
              CustomSnackBar.error(
                message: res['message'],
              ),
            );
          }
          return;
        }
        resSubject['data'].asMap().forEach((index, item) {
          listItemsSubject.add(item['titolo']);
        });

        itemsData.value = res['data'];
        itemsDataSubject.value = listItemsSubject;
      },
      [],
    );
    useEffect(() {
      List<Widget> listItems = [];
      if (selectedSubject.value == 'Seleziona Materia') {
        itemsData.value.asMap().forEach((index, item) {
          listItems.add(BookingCard(
              index,
              item['doc']['id'],
              (index, iddocente, idcorso, ora, data) =>
                  bookMeNow(index, iddocente, idcorso, data, ora),
              item['date'],
              item['actualTime'],
              item['doc']['nome'],
              item['doc']['cognome'],
              item['corso']['id'],
              item['corso']['titolo'],
              key: UniqueKey()));
        });
      } else {
        itemsData.value.asMap().forEach((index, item) {
          if (item['corso']['titolo'] == selectedSubject.value) {
            listItems.add(BookingCard(
                index,
                item['doc']['id'],
                (index, iddocente, idcorso, ora, data) =>
                    bookMeNow(index, iddocente, idcorso, data, ora),
                item['date'],
                item['actualTime'],
                item['doc']['nome'],
                item['doc']['cognome'],
                item['corso']['id'],
                item['corso']['titolo'],
                key: UniqueKey()));
          }
        });
      }

      itemsDataFiltered.value = listItems;
    }, [selectedSubject.value, itemsData.value]);
    useEffect(() {
      getDataAndRender();
      return null;
    }, [forceRerender.value]);

    useEffect(() {
      itemPositionsListener.itemPositions.addListener(controllerCallBack);
      return () => itemPositionsListener.itemPositions
          .removeListener(controllerCallBack);
    }, [controller]);
    return SafeArea(
      child: Scaffold(
          appBar: appBarMain(context, 'Prenotazioni disponibili'),
          body: SizedBox(
            height: size.height -
                kToolbarHeight - // top AppBar height
                MediaQuery.of(context).padding.top - // top padding
                kBottomNavigationBarHeight,
            child: Column(children: <Widget>[
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  const Text('Filtra per: ', style: TextStyle(fontSize: 20)),
                  const SizedBox(
                    width: 10,
                  ),
                  DropdownButton<String>(
                    value: selectedSubject.value,
                    icon: const Icon(Icons.arrow_downward),
                    elevation: 20,
                    style:
                        const TextStyle(color: Colors.deepPurple, fontSize: 20),
                    underline: Container(
                      height: 2,
                      color: Colors.deepPurpleAccent,
                    ),
                    onChanged: (String? newValue) {
                      selectedSubject.value = newValue!;
                    },
                    items: <String>[
                      "Seleziona Materia",
                      ...itemsDataSubject.value
                    ].map<DropdownMenuItem<String>>((String value) {
                      return DropdownMenuItem<String>(
                        value: value,
                        child: Text(value),
                      );
                    }).toList(),
                  ),
                ],
              ),
              Expanded(
                  child: ScrollablePositionedList.builder(
                      itemScrollController: controller,
                      itemCount: itemsDataFiltered.value.length,
                      itemPositionsListener: itemPositionsListener,
                      physics: const BouncingScrollPhysics(),
                      itemBuilder: (context, index) {
                        double scale = 1.0;
                        if (topContainer.value < 0.30 &&
                            topContainerIndex.value == index) {
                          scale = 0.5 + topContainer.value;
                          if (scale < 0) {
                            scale = 0;
                          } else if (scale > 1) {
                            scale = 1;
                          }
                        }

                        return Opacity(
                          opacity: scale,
                          child: Transform(
                            transform: Matrix4.identity()..scale(scale, scale),
                            alignment: Alignment.bottomCenter,
                            child: Align(
                                heightFactor: 1,
                                alignment: Alignment.center,
                                child: itemsDataFiltered.value[index]),
                          ),
                        );
                      })),
            ]),
          )),
    );
  }
}
