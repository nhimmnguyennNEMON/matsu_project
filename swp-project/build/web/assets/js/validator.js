// Đối tượng `Validator`
function Validator(options) {
    function getParent(element, selector) {
        while (element.parentElement) {
            if (element.parentElement.matches(selector)) {
                return element.parentElement;
            }
            element = element.parentElement;
        }
    }

    var selectorRules = {};

    // Hàm thực hiện validate
    function validate(inputElement, rule) {
        var errorElement = getParent(inputElement, options.formGroupSelector).querySelector(options.errorSelector);
        var errorMessage;

        // Lấy ra các rules của selector
        var rules = selectorRules[rule.selector];

        // Lặp qua từng rule & kiểm tra
        // Nếu có lỗi thì dừng việc kiểm
        for (var i = 0; i < rules.length; ++i) {
            switch (inputElement.type) {
                case 'radio':
                case 'checkbox':
                    errorMessage = rules[i](
                            formElement.querySelector(rule.selector + ':checked')
                            );
                    break;
                default:
                    errorMessage = rules[i](inputElement.value);
            }
            if (errorMessage)
                break;
        }

        if (errorMessage) {
            errorElement.innerText = errorMessage;
            getParent(inputElement, options.formGroupSelector).classList.add('invalid');
        } else {
            errorElement.innerText = '';
            getParent(inputElement, options.formGroupSelector).classList.remove('invalid');
        }

        return !errorMessage;
    }

    // Lấy element của form cần validate
    var formElement = document.querySelector(options.form);
    if (formElement) {
        // Khi submit form
        formElement.onsubmit = function (e) {
            e.preventDefault();

            var isFormValid = true;

            // Lặp qua từng rules và validate
            options.rules.forEach(function (rule) {
                var inputElement = formElement.querySelector(rule.selector);
                var isValid = validate(inputElement, rule);
                if (!isValid) {
                    isFormValid = false;
                }
            });

            if (isFormValid) {
                // Trường hợp submit với javascript
                if (typeof options.onSubmit === 'function') {
                    var enableInputs = formElement.querySelectorAll('[name]');
                    var formValues = Array.from(enableInputs).reduce(function (values, input) {

                        switch (input.type) {
                            case 'radio':
                                values[input.name] = formElement.querySelector('input[name="' + input.name + '"]:checked').value;
                                break;
                            case 'checkbox':
                                if (!input.matches(':checked')) {
                                    values[input.name] = '';
                                    return values;
                                }
                                if (!Array.isArray(values[input.name])) {
                                    values[input.name] = [];
                                }
                                values[input.name].push(input.value);
                                break;
                            case 'file':
                                values[input.name] = input.files;
                                break;
                            case 'date':
                                values[input.name] = input.value;
                                break;
                            default:
                                values[input.name] = input.value;
                        }

                        return values;
                    }, {});
                    options.onSubmit(formValues);
                }
                // Trường hợp submit với hành vi mặc định
                else {
                    formElement.submit();
                }
            }
        }

        // Lặp qua mỗi rule và xử lý (lắng nghe sự kiện blur, input, ...)
        options.rules.forEach(function (rule) {

            // Lưu lại các rules cho mỗi input
            if (Array.isArray(selectorRules[rule.selector])) {
                selectorRules[rule.selector].push(rule.test);
            } else {
                selectorRules[rule.selector] = [rule.test];
            }

            var inputElements = formElement.querySelectorAll(rule.selector);

            Array.from(inputElements).forEach(function (inputElement) {
                // Xử lý trường hợp blur khỏi input
                inputElement.onblur = function () {
                    validate(inputElement, rule);
                }

                // Xử lý mỗi khi người dùng nhập vào input
                inputElement.oninput = function () {
                    var errorElement = getParent(inputElement, options.formGroupSelector).querySelector(options.errorSelector);
                    errorElement.innerText = '';
                    getParent(inputElement, options.formGroupSelector).classList.remove('invalid');
                }
            });
        });
    }

}


// Định nghĩa rules-------------------------------------------------------------
// Nguyên tắc của các rules:
// 1. Khi có lỗi => Trả ra message lỗi
// 2. Khi hợp lệ => Không trả ra cái gì cả (undefined)

// ham bat buoc nhap vao o input nay
Validator.isRequired = function (selector, message) {
    return {
        selector: selector,
        test: function (value) {
            return value ? undefined : message || 'Please input the result!'
        }
    };
}

// ham check xem co phai email fpt hay khonh
Validator.isEmail = function (selector, message) {
    return {
        selector: selector,
        test: function (value) {
            var regex = /^\w+([\.-]?\w+)*@fpt.edu.vn/;
            return regex.test(value) ? undefined : message || 'Please input the result!';
        }
    };
}

// ham check ki tu dac biet ----var regex = /[^A-Za-z 0-9()-]/g;---- la format
Validator.isCharacterSpecial = function (selector, message) {
    return {
        selector: selector,
        test: function (value) {
            var regex = /[^A-Za-z 0-9()-]/g;
            return !regex.test(value) ? undefined : message || 'Character is special thus not allowed in param!';
        }
    };
}
Validator.isPositiveNumbers = function(selector,message){
    return{
        selector: selector,
        test: function (value) {
            return value > 0 ? undefined : message || 'Please enter a number greater than 0';
        }
    }
}
// ham check co phai date hay khong (dd/MM/yyyy)
Validator.isDate = function (selector, message) {
    return {
        selector: selector,
        test: function (value) {
            var regex = /^(?:(?:31(\/|-|\.)(?:0?[13578]|1[02]|(?:Jan|Mar|May|Jul|Aug|Oct|Dec)))\1|(?:(?:29|30)(\/|-|\.)(?:0?[1,3-9]|1[0-2]|(?:Jan|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec))\2))(?:(?:1[6-9]|[2-9]\d)?\d{2})$|^(?:29(\/|-|\.)(?:0?2|(?:Feb))\3(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\d|2[0-8])(\/|-|\.)(?:(?:0?[1-9]|(?:Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep))|(?:1[0-2]|(?:Oct|Nov|Dec)))\4(?:(?:1[6-9]|[2-9]\d)?\d{2})$/;
            return regex.test(value) ? undefined : message || 'Please input the Date (dd/MM/yyyy)';
        }
    };
}

// ham gioi han input dat khong duoc nhap date duoi ngay hien tai
Validator.minDate = function (selector, message) {
    return {
        selector: selector,
        test: function (value) {
            var date = new Date(value);
            var milliseconds = date.getTime();
            var currentNow = Date.now();
            var d = new Date();
            year = d.getFullYear();
            month = d.getMonth() + 1;
            day = d.getDate();
            return (milliseconds) >= (currentNow - 86400000) ? undefined : message || `Please input the no too ${day + "/" + month + "/" + year}`;
        }
    };
}

// ham confirm 2 o input có match voi nhau hay khong
Validator.compareDate = function (selector, getConfirmValue, message) {
    return {
        selector: selector,
        test: function (value) {
            var date = new Date(value);
            var toDate = date.getTime();
            var date1 = new Date(getConfirmValue);
            var fromDate = date1.getTime();
            return toDate >= fromDate ? undefined : message || 'Please input the result accurately!';
        }
    }
}

// ham gioi han input dat khong duoc nhap date qua ngay hien tai
Validator.maxDate = function (selector, message) {
    return {
        selector: selector,
        test: function (value) {
            var date = new Date(value);
            var milliseconds = date.getTime();
            var currentNow = Date.now();
            var d = new Date();
            year = d.getFullYear();
            month = d.getMonth() + 1;
            day = d.getDate();
            return milliseconds <= (currentNow + 86400000) ? undefined : message || `Please input the no too ${day + "/" + month + "/" + year}`;
        }
    };
}

Validator.compareDate = function (selector, getConfirmValue, message) {
    return {
        selector: selector,
        test: function (value) {
            var date = new Date(value.value);
            var toDate = date.getTime();
            var date1 = new Date(getConfirmValue.value);
            var fromDate = date1.getTime();
            return toDate >= fromDate ? undefined : message || 'Giá trị nhập vào không chính xác';
        }
    }
}


Validator.Year = function (selector, message) {
    return{
        selector: selector,
        test: function (value) {
            var d = new Date();
            year = d.getFullYear();
            return value == year || value == (year + 1) ? undefined : message || `Please input the no too ${year + 1} and less than ${year}`;
        }
    };
}

// ham gioi han input dat khong duoc nhap year qua nam hien tai
Validator.maxYear = function (selector, message) {
    return {
        selector: selector,
        test: function (value) {
            var d = new Date();
            year = d.getFullYear();
            return value <= year ? undefined : message || `Please enter student birth year not too ${year}`;
        }
    };
}

// ham nhap gia tri toi thieu
Validator.minLength = function (selector, min, message) {
    return {
        selector: selector,
        test: function (value) {
            return value.length >= min ? undefined : message || `Please input noo too ${min} character`;
        }
    };
}

// ham nhap gia tri toi da
Validator.maxLength = function (selector, max, message) {
    return {
        selector: selector,
        test: function (value) {
            return value.length <= max ? undefined : message || `Please input less than ${max} character`;
        }
    };
}

// ham confirm 2 o input có match voi nhau hay khong
Validator.isConfirmed = function (selector, getConfirmValue, message) {
    return {
        selector: selector,
        test: function (value) {
            return value === getConfirmValue() ? undefined : message || 'Please input the result accurately.';
        }
    }
}

// ham check xem co phải la file hay khong
Validator.isFiles = function (selector, message) {
    return {
        selector: selector,
        test: function (value) {
            var regex = /[^\\/]{3,}\.(xlsx)$/;
            return regex.test(value) ? undefined : message || 'This file need end by .xlsx';
        }
    };
}

// ham check nhap tu min - max
Validator.isValueRange = function (selector, min, max, message) {
    return {
        selector: selector,
        test: function (value) {
            return value >= min && value <= max ? undefined : message || `Please enter the value range ${min} - ${max}`;
        }
    };
}

// ham check nhap year k nho hon nam hien tai va k qua nam sau
Validator.Year = function (selector, message) {
    return{
        selector: selector,
        test: function (value) {
            var d = new Date();
            year = d.getFullYear();
            return value < year && value > year + 1 ? undefined : message || `Vui lòng nhập năm tối đa không quá ${year} và không nhỏ hơn ${year + 1}`;
        }
    };
}

// ham check nhap year k nho hon nam hien tai va k qua nam sau
Validator.Year = function (selector, message) {
    return{
        selector: selector,
        test: function (value) {
            var d = new Date();
            year = d.getFullYear();
            return value < year && value > year + 1 ? undefined : message || `Please input year noo too ${year} and less than ${year + 1}`;
        }
    };
}


