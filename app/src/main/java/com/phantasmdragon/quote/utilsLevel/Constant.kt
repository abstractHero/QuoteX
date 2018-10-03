/*
 * Copyright 2018 abstractHero
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.phantasmdragon.quote.utilsLevel

object Constant {

    //Retrofit endpoint
    const val BASE_URL = "http://api.forismatic.com/api/1.0/"
    const val DEFAULT_URL_PARAMETRES = "?method=getQuote&format=json"

    /**
     * Represents all types of colors from the array.xml file.
     * It's used to get a random color from there and fill the quote card with it.
     */
    enum class TypeColor {
        TYPE_200,
        TYPE_300,
        TYPE_400,
        TYPE_500,
        TYPE_600,
        TYPE_700,
        TYPE_800,
        TYPE_900,
        A100,
        A200,
        A400,
        A700;

        override fun toString() = name.removePrefix("TYPE_")
    }

    /**
     * Contains all possible outcomes that can appear when a network operation takes place.
     * By the way, the state called 'ERROR' means that there is a problem on the server, or some other issue.
     */
    enum class QueryState {
        PROCESS,
        NO_NETWORK,
        ERROR,
        DONE
    }

    const val DATABASE_NAME = "QuoteX"

    const val NOTIFICATION_PATH = "content://settings/system/notification_sound"

}