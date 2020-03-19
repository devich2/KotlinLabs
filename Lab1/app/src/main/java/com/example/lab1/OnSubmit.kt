package com.example.lab1

import android.os.Bundle
import androidx.fragment.app.Fragment

interface OnSubmit {
    fun Update(s:String, fragment: Fragment)
}