package com.smcdeveloper.nobinoapp.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.smcdeveloper.nobinoapp.ui.component.NobinoExpandableCard
import com.smcdeveloper.nobinoapp.ui.component.NobinoMainTitleHeader

@Composable
fun FaqScreen(navController: NavHostController)
{

    Column(
        modifier = Modifier.padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)




    )


    {

        NobinoMainTitleHeader("سوالات متداول",navController, modifier = Modifier)




        NobinoExpandableCard(
            title = "نحوه ثبت نام در سایت نوبینو چگونه است؟" ,
            description = "ثبت نام در سایت نوبینو با شماره همراه صورت می\u200Cگیرد. پس از انتخاب گزینه ورود و ثبت\u200Cنام (بالا و سمت چپ صفحه)، گزینه آبی رنگ «همین حالا حساب باز کنید» را انتخاب کرده و در صفحه باز شده، شماره تلفن همراه و بازه سنی خود را انتخاب کنید. پس از مطالعه قوانین و مقررات سایت نوبینو که با کلیک بر روی گزینه آبی رنگ «قوانین و مقررات سایت نوبینو را می\u200Cپذیرم» قابل دسترسی است و در صورت موافقت با قوانین و مقررات استفاده از سایت، تیک همان گزینه (قوانین و مقررات سایت نوبینو را میپذیرم) را فعال کنید. اینک با فشردن دکمه دریافت کد، یک کد تایید از طریق پیام کوتاه به تلفن همراه شما ارسال می\u200Cشود. پس از وارد کردن کد در صفحه باز شده و در صفحه جدید، رمز عبور انتخابی خود را وارد کنید. (رمز عبور شما باید ترکیبی از حروف و عدد و بیشتر از ۶ کاراکتر باشد.) بعد از انتخاب رمز عبور و فشردن گزینه تایید، وارد حساب کاربری خود در سایت نوبینو خواهید شد."





        )




        NobinoExpandableCard(
            title = "چگونه می\u200Cتوانم فیلم\u200Cهای نوبینو را دانلود کنم؟",
            description = "امکان دانلود از نوبینو وجود ندارد."






        )

        NobinoExpandableCard(
            title = "چرا باید اشتراک تهیه کنم؟",
            description = "شما با خرید اشتراک سایت نوبینو، به تمام آرشیو سایت دسترسی خواهید داشت و می\u200Cتوانید آن\u200Cها را بدون محدودیت دفعات پخش، تماشا نمایید. ضمن اینکه با تهیه اشتراک، به رعایت حقوق مادی و معنوی صاحبین آثار کمک کرده و باعث بهبود کیفیت تهیه و تولید محتواهای صوتی و تصویری خواهید شد. لازم به ذکر است که ممکن است برای تماشای برخی از محتواهای به\u200Cروز، مانند دوره\u200Cهای آموزشی و... نیاز به پرداخت جداگانه باشد."








        )

        NobinoExpandableCard(
            title = "چگونه باید اشتراک تهیه کنم؟",
            description = "با انتخاب گزینه قرمز رنگ موجود در رول (نوار منوی) بالای صفحه سایت، وارد صفحه خرید اشتراک می\u200Cشوید. در آنجا می\u200Cتوانید یکی از سه گزینه اشتراک یک ماهه، اشتراک سه ماهه و اشتراک شش ماهه را انتخاب کنید. با انتخاب هر یک از گزینه\u200Cها و فشردن گزینه پرداخت، وارد درگاه بانک شده و پس از پرداخت، دوباره به سایت نوبینو منتقل می\u200Cشوید و می\u200Cتوانید تمامی محتواهای موجود بر روی سایت را بدون محدودیت، تماشا نمایید."





        )



        NobinoExpandableCard(
            title = "در صورت دریافت مشکل یا پخش نشدن فیلم مشکل را چگونه برطرف کنم؟ ",
            description = "در این صورت با شماره تلفن پشتیبانی (۴۳۴۳۱۰۰۰، داخلی ۴۴۲) که در انتهای صفحه اول سایت نیز قرار دارد، تماس گرفته و مشکل خود را با کارشناسان ما درمیان بگذارید تا در سریع\u200Cترین زمان ممکن، مشکل به وجود آمده را بررسی کرده و اقدامات لازم را انجام بدهند."




        )






    }























}