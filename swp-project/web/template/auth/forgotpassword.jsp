<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Reset Password</title>
        <link rel="icon" href="/public/img/icon.png" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" />
        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/tw-elements/dist/css/index.min.css" />
        <script src="https://cdn.tailwindcss.com"></script>
        <script>
            tailwind.config = {
                theme: {
                    extend: {
                        fontFamily: {
                            sans: ['Inter', 'sans-serif']
                        }
                    }
                }
            }
        </script>
    </head>
    <body>
        <section class="h-screen">
            <div class="container px-6 py-12 h-full">
                <div class="flex justify-center items-center flex-wrap h-full g-6 text-gray-800">

                    <div class="md:w-9/12 lg:w-6/12 lg:ml-20">               

                        <!-- Email input -->
                        <div class=" w-full h-18 flex flex-row items-center bg-gray-100 mb-4 rounded-lg drop-shadow-lg hidden" id="card">
                            <div class="">
                                <img class="rounded-full h-10 w-10 mx-1 my-1" src="" alt="avatar" id="avatar">
                            </div>
                            <div class="text-base text-blue-800 w-fit mx-1" id="name">
                            </div>
                            <div class="text-base text-blue-800 w-fit mx-4" id="email">
                            </div>
                            <div class="text-base text-blue-800 w-fit mx-4">

                                <button type="button" onclick="chooseVia()" class=" inline-block px-6 py-2.5 bg-blue-700 text-white font-medium text-xs leading-tight uppercase rounded shadow-md hover:bg-cyan-900 hover:shadow-lg focus:bg-blue-800 focus:shadow-lg focus:outline-none focus:ring-0 active:bg-blue-800 active:shadow-lg transition duration-150 ease-in-out " >Reset</button>

                            </div>

                        </div>
                        <div class="mb-6">
                            <input
                                id="input1"
                                type="gmail"
                                class=" drop-shadow-lg form-control block w-full px-4 py-2 text-xl font-normal text-gray-700 bg-white bg-clip-padding border border-solid border-gray-300 rounded transition ease-in-out m-0 focus:text-gray-700 focus:bg-white focus:border-cyan-900 focus:outline-none"
                                placeholder="Email address"
                                />
                            <input
                                id="input2"
                                type="hidden"
                                />
                        </div>
                        <!-- Submit button -->
                        <button
                            onclick="sendData()"
                            type="submit"
                            class="drop-shadow-lg inline-block px-7 py-3 bg-blue-800 text-white font-medium text-sm leading-snug uppercase rounded shadow-md hover:bg-blue-900 hover:shadow-lg focus:bg-blue-800 focus:shadow-lg focus:outline-none focus:ring-0 active:bg-blue-900 active:shadow-lg transition duration-150 ease-in-out w-full"
                            data-mdb-ripple="true"
                            data-mdb-ripple-color="light"
                            >
                            Search
                        </button>

                        <div
                            class="flex items-center my-4 before:flex-1 before:border-t before:border-gray-300 before:mt-0.5 after:flex-1 after:border-t after:border-gray-300 after:mt-0.5"
                            >
                            <p class="text-center font-semibold mx-4 mb-0">OR</p>
                        </div>
                        <div class="text-center">
                            You are member? 
                            <a
                                href="auth?go=login"
                                class="text-blue-700 hover:text-blue-700 focus:text-blue-900 active:text-blue-900 duration-200 transition ease-in-out"
                                >Login</a
                            >      
                        </div>                       

                    </div>
                </div>

            </div>
        </section>
    </body>
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
    <script>

                                function  sendData() {
                                    let _email = $("#input1").val()
                                    $.ajax({
                                        type: "post",
                                        url: "/swp-project/auth?go=forgot-password",
                                        data: {
                                            email: _email,
                                        },
                                        success: function (res) {
                                            console.log(res);
                                            if (res.code == 1) {
                                             
                                                 swal({
                                                        title: "Sorry!",
                                                        text: "Email cua ban khong ton tai, vui long dang ki tai khoan",
                                                        icon: "error",
                                                    });
                                            } else
                                            {
                                                $("#card").css({"display": "flex"});
                                                $("#name").html(res.name);
                                                let email1 = res.email;
                                                let emailx = email1.substring(0, 3) + "********edu.vn";
                                                $("#email").html(emailx);
                                                $("#avatar").attr("src", res.avatar_link);
                                                $("#input1").val("");
                                                $("#input2").val(res.email);
                                            }


                                        }
                                    });
                                }
                                function chooseVia() {
                                    swal("How can we authenticate you?", {
                                        buttons: {
                                            email: {
                                                text: "Use email",
                                                value: "email",
                                            },
                                            otp: {
                                                text: "Two - Factor Authenticator",
                                                value: "otp",
                                            },
                                        },
                                    })
                                            .then((value) => {
                                                switch (value) {

                                                    case "email":
                                                        sendMail();
                                                        break;

                                                    case "otp":
                                                        checkQr();
                                                        break;

                                                }
                                            });
                                }
                                function checkQr() {
                                    swal("Input OTP in Authenticator Microsoft or Google, which you scan before:", {
                                        content: "input",
                                        icon: "https://www.krneki.net/Blog/image.axd?picture=/First1.PNG",
                                    }).then((value) => {
                                        $.ajax({
                                            type: "post",
                                            url: "/swp-project/auth?go=check-otp",
                                            data: {
                                                otp: value,
                                                email: $("#input2").val(),
                                            },
                                            success: function (res) {
                                                if (res.code == 0) {
                                                    swal({
                                                        title: "Sorry!",
                                                        text: "OTP not correct",
                                                        icon: "error",
                                                    });
                                                }
                                                if (res.code == 1) {
                                                    window.location.href="/swp-project/auth?go=reset-password&uuid="+res.uuid;
                                                }
                                            }
                                        })
                                    })
                                }
                                
                                function sendMail(){                                
                                    $.when(
                                            $.ajax({
                                            type: "post",
                                            url: "/swp-project/auth?go=send-mail-uuid",
                                            data: {
                                                email: $("#input2").val()
                                            },
                                            success: function (res) {
                                                if (res.code == 0) {
                                                    swal({
                                                        title: "Sorry!",
                                                        text: "Something not correct",
                                                        icon: "error",
                                                    });
                                                    
                                                }
                                                if (res.code == 1) {
                                                    swal({
                                                        title: "Good job!",
                                                        text: "Please check your email to take link to change password",
                                                        icon: "success",
                                                    });

                                                }
                                            }
                                        })
                                            ).done(waitMail())
                                                                                   
                                                                               //swal("Please wait a second"); 
                                    
                                }
                                
                                function waitMail(){
                                    swal("Please wait a second...");
                                }

                            





    </script>
    <script src="https://cdn.jsdelivr.net/npm/tw-elements/dist/js/index.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
</html>