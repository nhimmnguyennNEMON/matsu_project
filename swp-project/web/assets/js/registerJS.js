/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

const indicator = document.querySelector(".indicator");
const input = document.querySelector("#pass");
const week = document.querySelector(".week");
const medium = document.querySelector(".medium");
const strong = document.querySelector(".strong");
const text = document.querySelector(".text-indicator");

let regexWeek = /[a-z]/;
let regexMedium = /\d+/;
let regexStrong = /.[!,@,#,$,%,^,&,*,?,_,~,-,(,)]/;

function trigger() {
    if (input.value !== "") {
        indicator.style.display = "flex";
        text.style.display = "block";

        if (
                input.value.length <= 3 &&
                (input.value.match(regexWeek) ||
                        input.value.match(regexMedium) ||
                        input.value.match(regexStrong))
                ) {
            no = 1;
        }

        if (
                input.value.length >= 6 &&
                ((input.value.match(regexWeek) && input.value.match(regexMedium)) || 
                (input.value.match(regexMedium) && input.value.match(regexStrong)) || 
                (input.value.match(regexWeek) && input.value.match(regexStrong)))
                ) {
            no = 2;
        }

        if (
                input.value.length >= 6 &&
                input.value.match(regexWeek) &&
                input.value.match(regexMedium) &&
                input.value.match(regexStrong)
                ) {
            no = 3;
        }

        if (no === 1) {
            week.classList.add("active");
            text.textContent = "Your password is too week";
            text.classList.add("week");
        }

        if (no === 2) {
            medium.classList.add("active");
            text.textContent = "Your password is medium";
            text.classList.add("medium");
        } else {
            medium.classList.remove("active");
            text.classList.remove("medium");
        }

        if (no === 3) {
            medium.classList.add("active");
            strong.classList.add("active");
            text.textContent = "Your password is strong";
            text.classList.add("strong");
        } else {
            strong.classList.remove("active");
            text.classList.remove("strong");
        }

    } else {
        indicator.style.display = "none";
        text.style.display = "none";
    }
}

