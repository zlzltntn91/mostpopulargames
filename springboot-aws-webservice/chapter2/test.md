TDD와 단위테스트는 다른이야기

## TDD
  테스트가 주도하는 개발, 테스트 코드를 먼저 작성
  - red green cycle
    1. 항상 실패하는 테스트를 먼저 작성하고 red
    2. 테스트가 통과하는 프로덕션 코드를 작성하고 green
    3 .테스트가 통과하면 프로덕션 코드를 리펙토링 refactor

## 단위테스트
  TDD의 첫번쨰 단계인 기능 단위의 테스트 코드를 작성
  - 꼭 테스트코드를 먼저 작성해야 하느것도 아니고 리펙토링도 포함되지 않음
순수하게 테스트 코드만 작성
    
https://repo.yona.io/doortts/blog/issue/1

그레이들 버전 4점대로 변경
5버전시 롬복테스트 실패뜨네

- TDD와 단위테스트란
- 스프링 부트환경에서 테스트코드 작성방법

clientId = zya3ijhz2mutp9vxcg5zyhguqt9tou
accessToken = s46yaix8pot0u4o40o2lt761ecyrb3

https://id.twitch.tv/oauth2/authorize
?client_id=zya3ijhz2mutp9vxcg5zyhguqt9tou
&redirect_uri=http://localhost
&response_type=token
&scope=analytics:read:games