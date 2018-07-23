package vn.linh.domain.usecase

import vn.linh.domain.usecase.input.EmptyInput

class GetRepoUseCase : UseCase<EmptyInput, Long>() {

    override fun buildDataStream(input: EmptyInput): Long {
        return 1
    }
}