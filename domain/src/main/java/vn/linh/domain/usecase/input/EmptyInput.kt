package vn.linh.domain.usecase.input

class EmptyInput private constructor() : Input() {

        companion object {

            fun instance(): EmptyInput {
                return EmptyInput()
            }
        }
    }