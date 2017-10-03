/*
 *  Copyright 2017 Patrick Favre-Bulle
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package at.favre.tools.dice.service.anuquantum;

import java.util.List;

public final class AnuQuantumResponse {
    public final String type;
    public final Integer length;
    public final Integer size;
    public final List<String> data;
    public final boolean success;

    AnuQuantumResponse(String type, Integer length, Integer size, List<String> data, boolean success) {
        this.type = type;
        this.length = length;
        this.size = size;
        this.data = data;
        this.success = success;
    }
}
