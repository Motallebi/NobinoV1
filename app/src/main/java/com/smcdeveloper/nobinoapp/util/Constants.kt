package com.smcdeveloper.nobinoapp.util

import androidx.compose.ui.unit.dp
import com.smcdeveloper.nobinoapp.data.model.AudioSubtitle

object Constants {

    const val ENGLISH_LANG = "en"
    const val NOBINO_LOG_TAG = "NobinoApp"
    const val NOBINO_LOG_TAG1 = "NobinoApp1"
    const val LOG_TAG_IMAGES = "NobinoAppImage"
    const val MovieCategory = "MOVIE"
    const val SeriesCategory = "MOVIE"
    const val SeasonCategory = "MOVIE"
    const val MovieCategory1 = "MOVIE"
    const val IMAGE_WITDTH=156
        //217
    const val IMAGE_HIGHT=217
        //260



    var USER_LOGIN_STATUS= false

     val FILTER_AUDIO_SUBTITLE=
        listOf(
            AudioSubtitle(id = "FARSI", value = "FARSI", name = "فارسی"),
            AudioSubtitle(id = "ENGLISH", value = "ENGLISH", name = "انگلیسی"),
            AudioSubtitle(id = "ARABIC", value = "ARABIC", name = "عربی"),
            AudioSubtitle(id = "FRENCH", value = "FRENCH", name = "فرانسه"),
            AudioSubtitle(id = "TURKISH", value = "TURKISH", name = "ترکیه"),
            AudioSubtitle(id = "RUSSIAN", value = "RUSSIAN", name = "روسیه"),
            AudioSubtitle(id = "SPANISH", value = "SPANISH", name = "اسپانیا"),
            AudioSubtitle(id = "KOREAN", value = "KOREAN", name = "کره"),
            AudioSubtitle(id = "GERMANY", value = "GERMANY", name = "آلمان"),
            AudioSubtitle(id = "INDIAN", value = "INDIAN", name = "هند"),
            AudioSubtitle(id = "JAPANESE", value = "JAPANESE", name = "ژاپن"),
            AudioSubtitle(id = "CHINESE", value = "CHINESE", name = "چینی"),
            AudioSubtitle(id = "ITALIAN", value = "ITALIAN", name = "ایتالیایی"),
            AudioSubtitle(id = "NORWEGIAN", value = "NORWEGIAN", name = "نروژی")
        )







    const val DEFAULT_IMAGE_POSETR="https://vod.nobino.ir/vod/IMAGES/2025-4/13530/13530_1745382041947_IMAGES_BANNER_MOBILE.jpg"





    // Create a constant list of AudioSubtitle items


















    const val PERSIAN_LANG = "fa"
    const val DATASTORE_NAME = "NOBINO_DATA_STORE"
   // const val BASE_URL = "https://stg.nobino.ir/"
    const val BASE_URL = "https://nobino.ir/"
    //const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    const val PURCHASE_URL = "https://api.zarinpal.com/pg/v4/payment/"
    const val ZARINPAL_PAYMENT_URL = "https://www.zarinpal.com/pg/StartPay/"
    const val TIMEOUT_IN_SECOND: Long = 60
  //  const val API_KEY = BuildConfig.X_API_KEY
   // const val KEY = BuildConfig.KEY
 //   const val IV = BuildConfig.IV
    const val SHOPPING_CART_TABLE = "shopping_cart"
    const val FAVORITE_LIST_TABLE = "favorite_list_table"
    const val DATABASE_NAME = "digikala_db"
    var USER_LANGUAGE = "USER_LANGUAGE"
    var USER_TOKEN ="USER_TOKEN"
    var USER_ID = "USER_ID"
    var USER_PROFILE_ID = ""

    var USER_FIRST_NAME = "USER_FIRST_NAME"
    var USER_LAST_NAME = "USER_LAST_NAME"
   const val IMAGE_BASE_URL= "https://vod.nobino.ir/vod/"







    var USER_PHONE = "USER_PHONE"
    var USER_PASSWORD = "USER_PASSWORD"
    const val ZARINPAL_MERCHANT_ID = "ce7101df-cb08-41f8-a20b-b21995173d8f"

    const val PRODUCT_COMMENTS = "PRODUCT_COMMENTS"
    const val USER_COMMENTS = "USER_COMMENTS"

    const val DIGIJET_URL = "https://www.digikalajet.com/user/address"
    const val AUCTION_URL = "https://www.digistyle.com/sale-landing/?utm_source=digikala&utm_medium=circle_badge&utm_campaign=style&promo_name=style&promo_position=circle_badge"
    const val DIGIPAY_URL = "https://www.mydigipay.com/"
    const val PINDO_URL = "https://www.pindo.ir/?utm_source=digikala&utm_medium=circle_badge&utm_campaign=pindo&promo_name=pindo&promo_position=circle_badge"
    const val SHOPPING_URL = "https://www.digikala.com/landings/village-businesses/?promo_name=boomi-landing&promo_position=circle_badge"
    const val GIFT_CARD_URL = "https://www.digikala.com/landing/gift-card-landing/?promo_name=gift_landing&promo_position=circle_badge"
    const val DIGIPLUS_URL = "https://www.digikala.com/plus/landing/"
    const val MORE_URL = "https://www.digikala.com/mehr/"


    const val DIGI_FAQ = "https://www.digikala.com/faq/"
    const val DIGI_PRIVACY = "https://www.digikala.com/page/privacy/"
    const val DIGI_TERMS = "https://www.digikala.com/page/terms/"
    const val DIGI_TURLEARN = "https://truelearn.ir/"
    const val DIGI_BUG = "https://www.digikala.com/page/bug-report/"
    const val DIGI_SCORE = "https://cafebazaar.ir/app/com.digikala"

    const val DIGI_WALLET = "https://www.mydigipay.com/"
    const val DIGI_CLUB = "https://www.digikala.com/digiclub/"
    const val TURLEARN_CONTACT_US = "https://truelearn.ir/contact/"

    var isFromPurchase = false
    var afterPurchaseUrl = ""
    var purchaseOrderId = ""
    var purchasePrice = ""










}