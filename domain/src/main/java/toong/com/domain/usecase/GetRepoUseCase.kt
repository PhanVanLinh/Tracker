package toong.com.domain.usecase

import toong.com.domain.usecase.input.EmptyInput

class GetRepoUseCase : UseCase<EmptyInput, Long>() {

    override fun buildDataStream(input: EmptyInput): Long {
        return 1
    }
}