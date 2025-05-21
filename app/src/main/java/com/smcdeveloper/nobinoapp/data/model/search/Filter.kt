package com.smcdeveloper.nobinoapp.data.model.search

import com.smcdeveloper.nobinoapp.ui.screens.search.FilterType

data class Filter(val filterName:String,val filterType:FilterType, val filterId:String="", val filterTagid:String="")
