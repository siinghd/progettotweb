import 'package:appwestu/pages/auth/UserLoggedInState.dart';
import 'package:appwestu/utils/GenericRequests.dart';
import 'package:flutter/material.dart';
import 'package:flutter_hooks/flutter_hooks.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:provider/provider.dart';
import 'package:appwestu/widgets/widgets.dart';
import 'package:go_router/go_router.dart';
import 'package:top_snackbar_flutter/custom_snack_bar.dart';
import 'package:top_snackbar_flutter/top_snack_bar.dart';

class SignInPage extends HookWidget {
  SignInPage(this.toggleView, {Key? key}) : super(key: key);
  
  final Function toggleView;

  final _formkey = GlobalKey<FormState>();

  @override
  Widget build(BuildContext context) {
    
    final isLoading = useState(false);
    final emailController = useTextEditingController(text: 'test@tester.com');
    final passwordController = useTextEditingController(text: '123456');
    final userLoggedInState = useMemoized(() => UserLoggedInState());
    final _onSubmit = useMemoized(
      () => () async {
        if (_formkey.currentState!.validate()) {
          isLoading.value = true;
          _formkey.currentState!.save();
          userLoggedInState.setEmail = emailController.text;
          userLoggedInState.setPassword = passwordController.text;
          var res = await GenericRequests.genericPost('/login', {
            'email': emailController.text,
            'password': passwordController.text
          });
          isLoading.value = false;
          if (res['status'] == 'fail') {
            showTopSnackBar(
              context,
              CustomSnackBar.error(
                message: res['message'],
              ),
            );
            return;
          }
          UserLoggedInState.saveUserRoleAsLogged(res['data'][0]['ruolo']);
          context.read<UserLoggedInState>().setRole = res['data'][0]['ruolo'];
        }
      },
      [_formkey],
    );

    useEffect(() {
      void getValue() async {
        int? role = await UserLoggedInState.getUserRoleAsLogged();
        if (role == null) {
          context.read<UserLoggedInState>().setRole = -1;
        } else {
          context.read<UserLoggedInState>().setRole = role;
        }
      }

      getValue();
    }, []);
    return Scaffold(
      backgroundColor: Color.fromARGB(255, 4, 78, 112),
      body: isLoading.value
          ? const Center(
              child: CircularProgressIndicator(),
            )
          : Container(
              padding: const EdgeInsets.symmetric(horizontal: 24.0),
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: <Widget>[
                  Center(
                      child: SizedBox(
                          height: 100.0,
                          width: 100.0,
                          child: SvgPicture.asset('assets/images/image.svg',
                              semanticsLabel: 'Logo'))),
                  Form(
                    key: _formkey,
                    child: Column(
                      children: [
                        TextFormField(
                            controller: emailController,
                            validator: (value) {
                              if (!RegExp(
                                      r"^[a-zA-Z0-9.a-zA-Z0-9.!#$%&'*+-/=?^_`{|}~]+@[a-zA-Z0-9]+\.[a-zA-Z]+")
                                  .hasMatch(value!)) {
                                return "Inserisci un'email corretta";
                              }
                            },
                            decoration: textfieldDecorationForm("E-mail"),
                            style: simpleTextStyle()),
                        const SizedBox(
                          height: 15.0,
                        ),
                        TextFormField(
                          controller: passwordController,
                          decoration: textfieldDecorationForm('Password'),
                          style: simpleTextStyle(),
                          obscureText: true,
                        ),
                      ],
                    ),
                  ),
                  const SizedBox(
                    height: 20.0,
                  ),
                  Container(
                    alignment: Alignment.centerRight,
                    child: GestureDetector(
                      onTap: () => context.goNamed('bookingsAvailable'),
                      child: Container(
                        padding: const EdgeInsets.symmetric(
                            horizontal: 16.0, vertical: 8.0),
                        child: Text(
                          'Accedi come ospite',
                          style: simpleTextStyle(),
                        ),
                      ),
                    ),
                  ),
                  const SizedBox(
                    height: 20.0,
                  ),
                  myButton(context, () => _onSubmit(), 'Accedi',width:200.0),
                  const SizedBox(
                    height: 15.0,
                  ),
                  // Row(
                  //   mainAxisAlignment: MainAxisAlignment.center,
                  //   children: <Widget>[
                  //     const Text(
                  //       'Sei nuovo? ',
                  //       style: TextStyle(color: Colors.white, fontSize: 16.0),
                  //     ),
                  //     GestureDetector(
                  //       onTap: () {
                  //         toggleView();
                  //       },
                  //       child: const Text(
                  //         'Registrati adesso!',
                  //         style: TextStyle(
                  //             color: Colors.white,
                  //             fontSize: 16.0,
                  //             decoration: TextDecoration.underline),
                  //       ),
                  //     ),
                  //   ],
                  // ),
                ],
              ),
            ),
    );
  }
}
